package com.study.usefulknowledge.消息.org_apache_qpid_amqp_1_0_jms_impl_ConnectionFactoryImpl包;

//import net.xhms.common.ConfigUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.qpid.amqp_1_0.jms.impl.ConnectionFactoryImpl;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.apache.qpid.amqp_1_0.jms.impl.TopicImpl;

import javax.jms.*;
//import java.text.SimpleDateFormat;
//import java.util.Date;

/**
 * Created by wingsby on 2018/1/12.
 */
public  class AMPQTextMessageListener   {
    private static final Log logger = LogFactory.getLog(Publish.class);
    private static final String user = "app";
    private static final String password = "app82193302";
    private static final String host = "124.254.45.82";
    private static final int port = 61613;
    private static final String topic = "app.test";

    public static void sub(String queuename) {
        String destination = "queue://" + queuename;
        ConnectionFactoryImpl factory = new ConnectionFactoryImpl(host, port, user, password);
        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = null;
            if( destination.startsWith("topic://") ) {
                dest = new TopicImpl(destination);
            } else {
                dest = new QueueImpl(destination);
            }
            MessageConsumer consumer = session.createConsumer(dest);
            long start = System.currentTimeMillis();
            long count = 1;
            logger.info("Waiting for messages...");
            while (true) {
                Message msg = consumer.receive();
                if (msg != null) {
                    if (msg instanceof TextMessage) {
                        String message = ((TextMessage) msg).getText();
//                        this.receiveTextMsg(message);
                        logger.info("接收消息为:" + message);
                    } else {
                        logger.error("消息不是TEXTMESSAGE类型，请检查程序");
                    }
                } else {
                    logger.warn("消息为空");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Apollo 连接失败!");
            return;
        }
    }

    public static void main(String[] args) {
        try {

            AMPQTextMessageListener message = new AMPQTextMessageListener();
            message.sub("app.test");
//                message.sub("app.monitor");
        } catch (Exception e) {
            logger.error("GTSPP消息接收异常！", e);
        }
    }

}
