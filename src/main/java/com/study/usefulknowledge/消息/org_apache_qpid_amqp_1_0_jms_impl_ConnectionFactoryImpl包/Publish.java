package com.study.usefulknowledge.消息.org_apache_qpid_amqp_1_0_jms_impl_ConnectionFactoryImpl包;

//import net.xinhong.meteoserve.common.tool.ConfigUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.qpid.amqp_1_0.jms.Destination;
import org.apache.qpid.amqp_1_0.jms.MessageProducer;
import org.apache.qpid.amqp_1_0.jms.Session;
import org.apache.qpid.amqp_1_0.jms.TextMessage;
import org.apache.qpid.amqp_1_0.jms.impl.ConnectionFactoryImpl;
import org.apache.qpid.amqp_1_0.jms.impl.ConnectionImpl;
import org.apache.qpid.amqp_1_0.jms.impl.QueueImpl;
import org.apache.qpid.amqp_1_0.jms.impl.TopicImpl;

import javax.jms.JMSException;

public class Publish {
    private static final Log logger = LogFactory.getLog(Publish.class);
    private static final String user = "app";
    private static final String password = "app82193302";
    private static final String host = "124.254.45.82";
    private static final int port = 61613;
    private static final String topic = "ec.geo";

    public Publish() {
    }

    public static void pub(String msgContent, boolean persistent, DestinationEnum dest) {
        pub(msgContent, topic, persistent, dest);
    }

    public static void pub(String msgContent, String topicStr, boolean persistent, DestinationEnum dest) {
        ConnectionImpl connection = null;

        try {
            ConnectionFactoryImpl factory = new ConnectionFactoryImpl(host, port, user, password);
            Object topic;
            if (dest == DestinationEnum.TOPIC) {
                topic = new TopicImpl(DestinationEnum.TOPIC.getCode() + topicStr);
            } else {
                topic = new QueueImpl(DestinationEnum.QUEUE.getCode() + topicStr);
            }

            connection = factory.createConnection(user, password);
            connection.start();
            Session session = connection.createSession(false, 1);
            MessageProducer producer = session.createProducer((Destination)topic);
            if (persistent) {
                producer.setDeliveryMode(2);
            } else {
                producer.setDeliveryMode(1);
            }

            TextMessage msg = session.createTextMessage(msgContent);
            producer.send(msg);
        } catch (JMSException var18) {
            logger.error("消息发送失败！", var18);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException var17) {
                var17.printStackTrace();
            }

        }

    }

    public static enum DestinationEnum {
        TOPIC("TOPIC", "topic://", "广播方式发送消息，客户端不在线的话，消息会丢失"),
        QUEUE("QUEUE", "queue://", "点对点方式发送，一条消息只能一个客户端接收");

        private String ename;
        private String code;
        private String description;

        public String getEname() {
            return this.ename;
        }

        public String getCode() {
            return this.code;
        }

        public String getDescription() {
            return this.description;
        }

        private DestinationEnum(String ename, String code, String description) {
            this.ename = ename;
            this.code = code;
            this.description = description;
        }

        public static String getCodeByName(String name) {
            DestinationEnum[] var1 = values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                DestinationEnum dest = var1[var3];
                if (dest.getEname().equals(name)) {
                    return dest.getCode();
                }
            }

            return null;
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            JSONObject geoMsg = new JSONObject();
            geoMsg.put("geoPath" , "/xinhong");
            geoMsg.put("ip", "testip");
            geoMsg.put("date", "20190516");
            Publish.pub(geoMsg.toString(), "app.test", true, DestinationEnum.QUEUE);
        }

    }
}
