package com.study.usefulknowledge.远程连接通信json_mysql_ftp_redis.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Tuple;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by liuso on 2017/7/14.
 */

    public class JedisClusterFastDemo {
        JedisCluster jedis = null;

        @Before
        public void conn() {
            Set<HostAndPort> nodes = new HashSet<HostAndPort>();
//            nodes.add(new HostAndPort("124.254.45.82", 6379));
            nodes.add(new HostAndPort("192.6.1.21", 6379));
            nodes.add(new HostAndPort("192.6.1.21", 6390));
            nodes.add(new HostAndPort("192.6.1.21", 6391));
            nodes.add(new HostAndPort("192.6.1.21", 6392));
            nodes.add(new HostAndPort("192.6.1.21", 6393));
            nodes.add(new HostAndPort("192.6.1.21", 6394));
            jedis = new JedisCluster(nodes);
        }

        /**
         * 操作String
         */
        @Test
        public void operStr() {
            DecimalFormat format = new DecimalFormat("000");
            conn();
            // 添加一条数据
//            jedis.set("username", "jonychen");
//            jedis.set("username1", "jonychen");
            // 获取一条数据
//            ScanResult<Map.Entry<String, String>> str =  jedis.hscan("username" , "jonychen");
            String  strData = null;
            for (int i = 0; i < 1000; i++) {
                String i_string = format.format(i);
//                strData = jedis.hget("wavewatch3:data" + ":" + "20180624"+"_"+i_string,"30.0_130.0");
                strData = jedis.hget("xhgfs:fc:point:"+"2018062412"+"003" , "82.00_99.00");
                List<String> s = jedis.hvals("*"+ "wavewatch3:data" + "*");

                if(strData !=null && !strData.isEmpty()){
                    System.out.println("第："+i);
                }
            }
//            String  strData = jedis.hget("wavewatch3:data" + ":" + "20180615"+"_"+"007","30.0_130.0");
            String username = jedis.get("username");
            System.out.println("查询的value：" + strData);
            Long total_animals = jedis.llen("name");
//            Long total_user = jedis.llen("username");
            // 删除
//            jedis.del("username");
//            jedis.del("username1");
            System.out.println();
        }

        /**
         * 操作hash
         */
        @Test
        public void operHash() {
            // 添加一条
            jedis.hset("goodsInfo", "goodsName", "403-超级手机");
            // 获取一条
            String goodsName = jedis.hget("goodsInfo", "goodsName");
            System.out.println("商品名称" + goodsName);

            Map<String, String> hash = new HashMap<String, String>();
            hash.put("orderSn", "20171226122301");
            hash.put("orderStatus", "提交预订单");

            // 添加多条
            jedis.hmset("orderInfo", hash);
            System.out.println("---------------");
            // 获取多条
            List<String> strList = jedis.hmget("orderInfo", "orderSn", "orderStatus");
            for (String string : strList) {
                System.out.println(string);
            }
            System.out.println("---------------");
            // 获取全部

            Map<String, String> orderInfoMap = jedis.hgetAll("orderInfo");
            for (Map.Entry<String, String> entry : orderInfoMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

            // 删除
            jedis.hdel("orderInfo", "orderStatus");
        }

        /**
         * 操作list
         */
        @Test
        public void operList() {
            // 添加
            for (int i = 0; i < 10; i++) {
                jedis.lpush("animals", "dog" + i, "cat" + i, "fish" + i);
            }

            // 获取
            String reString = jedis.rpop("animals");
            System.out.println(reString);
            // 分页查询 start：起始条数 end :结束条数
            List<String> strList = jedis.lrange("animals", 0, 9);
            for (String string : strList) {
                System.out.println(string);
            }
            System.out.println("----------------");
            // 获取总条数
            Long total = jedis.llen("animals");
            System.out.println("总条数" + total);
            // 删除
            jedis.lrem("animals", 1, "dog0");
        }

        /**
         * 操作set
         */
        @Test
        public void operSet() {
            // 添加
            jedis.sadd("members", "xiaoming", "xiaohua", "xiaohui", "xiaochen");
            // 获取
            Set<String> members = jedis.smembers("members");
            for (String string : members) {
                System.out.println(string);
            }
            // 删除
            jedis.srem("members", "xiaohui");
        }

        /**
         * 操作sorted set-自动排序
         */
        @Test
        public void operSortedSet() {
            Map<String, Double> scoreMembers = new HashMap<String, Double>();
            scoreMembers.put("小明", 89D);
            scoreMembers.put("xiaopeng", 93D);
            scoreMembers.put("小胡", 88D);
            // 添加
            jedis.zadd("score", scoreMembers);
            // 获取start ：起始条数 end：结束条数 按分数升序查询
            Set<String> strSet = jedis.zrange("score", 0, 1);
            for (String string : strSet) {
                System.out.println(string);
            }
            System.out.println("-------------");
            // 降序查询，并获取成员的分数
            Set<Tuple> tupleSet = jedis.zrevrangeWithScores("score", 0, 1);
            for (Tuple tuple : tupleSet) {
                // 成员
                String element = tuple.getElement();
                Double score = tuple.getScore();
                System.out.println(element + ":" + score);
            }
            System.out.println("-----------------");
            // 获取总条数
            Long total = jedis.zcard("score");
            System.out.println("总条数：" + total);
            // 删除
            jedis.zrem("score", "xiaopeng");
        }
    }


