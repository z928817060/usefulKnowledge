package com.study.usefulknowledge.消息;

import com.alibaba.fastjson.JSONObject;
import com.argo.argoprocess.common.ResourcesUtil;
import org.fusesource.stomp.jms.*;
import org.fusesource.stomp.jms.message.StompJmsBytesMessage;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service
public class Listeners implements Runnable {
    //public class Listeners  {
    public Listeners() {
    }

    @Autowired
    DealServiceImpl dealService;

    @Autowired
    ResourcesUtil resourcesUtil;

    @Autowired
    Listeners listeners;

    public static boolean isCon = false;
    public static int count = 0;                                        //单次消费个数
    public static final int countMax = 50;                               //单次心跳最大消费个数 50
    public static DateTime lastTime = new DateTime();                   //最近消费时间
    public  static final long spaceTime = 15 * 60 * 1000L;               //允许最大空闲时间  15min
    private boolean firstRunFlag = true;                                //第一次执行flag
    private Thread tp = null;

    private String env(String key, String defaultValue) {
        String rc = System.getenv(key);
        if (rc == null)
            return defaultValue;
        return rc;
    }

    private String arg(String[] args, int index, String defaultValue) {
        if (index < args.length)
            return args[index];
        else
            return defaultValue;
    }

    @Override
    public void run() {
        count = 0;
        lastTime = new DateTime();
        Connection connection = null;
        try {
            isCon = true;
            String user = env("APOLLO_USER", resourcesUtil.getApollo_user());
            String password = env("APOLLO_PASSWORD", resourcesUtil.getApollo_password());
            String host = env("APOLLO_HOST", resourcesUtil.getApollo_host());
            int port = Integer.parseInt(env("APOLLO_PORT", resourcesUtil.getApollo_port()));
//            String user = env("APOLLO_USER", "app");
//            String password = env("APOLLO_PASSWORD","app82193302");
//            String host = env("APOLLO_HOST", "124.254.45.82");
//            int port = Integer.parseInt(env("APOLLO_PORT", "61613"));

            String destination = arg(new String[]{}, 0, "/queue/app.argo.down");
//            String destination = arg(new String[]{}, 0, "/queue/app.test");
            StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
            factory.setBrokerURI("tcp://" + host + ":" + port);
            factory.setDisconnectTimeout(1L);
            connection = factory.createConnection(user, password);
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = new StompJmsDestination(destination);

            MessageConsumer consumer = session.createConsumer(dest);
            System.out.println("Waiting for messages...");
            while (count < countMax) {
                try {
                    count++;
                    lastTime = new DateTime();
                    Message msg = consumer.receive();                                       //无法catch
                    StompJmsBytesMessage bytesMessage = (StompJmsBytesMessage) msg;
                    String message = bytesMessage.getContent().ascii().toString();
                    JSONObject jsonObject = JSONObject.parseObject(message);
                    System.out.println("消费消息： " + message);
                    dealService.parseData(jsonObject);                                      //具体业务
                    Thread.sleep(1000 * 5);
                } catch (Exception e) {
                    isCon = false;
                    System.out.println("解析json有误！！！");
                }
            }
        } catch (Exception e) {
            isCon = false;
            System.out.println("消息接收出错了！！");
        } finally {
            System.out.println("    finally~~~~~~     此次线程结束"   );
            try {
                connection.close();
                isCon = false;
            } catch (Exception e) {
                isCon = false;
                System.out.println(e.toString());
            }
        }
    }

    /**
     * 查看线程状态，并且启动关闭线程
     */
    public void dojob() {
        try {
            if (firstRunFlag) {
                tp = new Thread(listeners);
                tp.start();
                firstRunFlag = false;
            }
            DateTime now = new DateTime();
            long timeDif = now.getMillis() - Listeners.lastTime.getMillis();
            if (Listeners.count >= countMax || timeDif > spaceTime) {
                if (Listeners.count >= countMax) System.out.println("结束原因：   大于等于" + countMax + "！！！");
                if (timeDif > 15 * 60 * 1000) System.out.println("结束原因：   等待时间超过15min ！！！");
                if (tp != null && tp.isAlive()) {
                    tp.stop();
                }
                Thread.sleep(1000 * 4 * 1);
                System.out.println("结束上一个线程~" + "上个线程是否开启 isAlive：" + tp.isAlive());
                System.out.println("开始新线程~~~");
                tp = new Thread(listeners);
                tp.start();
                System.out.println("    新线程是否开启 isAlive：" + tp.isAlive());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) {
        Listeners listeners = new Listeners();
        listeners.run();
    }
}
