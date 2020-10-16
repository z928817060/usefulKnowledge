package com.study.usefulknowledge.MysqlTest;

import NMC.NMC_Process;
import com.alibaba.fastjson.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import tools.*;

import java.sql.*;
import java.util.List;

/**
 * @Autor rongxiaokun
 * @Date 2018/7/23  10:50
 * 当前实况
 */
public class NMC_CurrentCondition extends NMC_Process {
    private static final Logger LOGGER = LoggerFactory.getLogger(NMC_CurrentCondition.class);

    @Override
    public JSONObject catchJsonMSG(String station) {
        JSONObject json = JSONUtil.readJsonFromUrl(PropertyUtil.getProperty("URL_CurrentCondition") + station);
        JSONObject json_aqi = JSONUtil.readJsonFromUrl(PropertyUtil.getProperty("URL_CurrentCondition_aqi") + station);
        if(json == null|| json_aqi==null  ||json.isEmpty() ||json_aqi.isEmpty())return new JSONObject();                                                //对每个json,有空则全空
        json.put("aqi" , json_aqi);
        return json;
    }

    @Override
    public boolean importMysql(List<JSONObject> jsonList) {
        Connection connection =null;
        Statement statement = null;
        for (int mysql_i = 0; mysql_i < 2; mysql_i++) {                                              //录入两个mysql
            if(PropertyUtil.getProperty("NMC_CurrentCondition_mysql"+mysql_i+"_lock").equals("0"))continue;           //设置一个录入开关
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection= DriverManager.getConnection(PropertyUtil.getProperty("NMC_CurrentCondition_mysql"+mysql_i+"_url"),
                        PropertyUtil.getProperty("NMC_CurrentCondition_mysql"+mysql_i+"_name"),
                        PropertyUtil.getProperty("NMC_CurrentCondition_mysql"+mysql_i+"_password"));
                //实现数据库连接，实现增删改查Statement
                statement=connection.createStatement();
                //执行查询语句，得到查询结果
                String date = new DateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH"));
                for (int i = 0; i < jsonList.size(); i++) {
                    JSONObject json = jsonList.get(i);
                    if (json.isEmpty() ||json == null || json.get("station") == null  || JSONObject.parseObject( json.get("station").toString()).get("code") == null)
                        continue;
                    String stationCode = JSONObject.parseObject( json.get("station").toString()).get("code").toString();
                    String sql = "SELECT * FROM NMC_STATION_REAL WHERE otime LIKE "+"'%"+date+"%'"  +" AND stationcode LIKE "+"'%"+stationCode+"%'" ;  //+" AND stationcode LIKE "+"'%"+stationCode+"%'"
                    ResultSet resultSet = statement.executeQuery(sql);
                    resultSet.last();
                    if (resultSet.getRow()>0){
                        updatesql(json, connection, resultSet);
                    }else {
                        addsql(json, connection);
                    }
                }
                //遍历输出查询结果，一行一行读
//            resultSet.last();
//            int dataLen = resultSet.getRow();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (connection!=null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection!=null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean importRedis(List<JSONObject> jsonList) {
        synchronized (JedisUtil.class) {
            JedisCluster jedis = null;
            jedis = JedisUtil.connRedis();
            for (int i = 0; i < jsonList.size(); i++) {
                JSONObject json = jsonList.get(i);
                if (json == null || json.isEmpty() || json.get("station") == null || json.get("weather") == null || json.get("wind") == null || json.get("warn") == null)
                    continue;                                        //json可能取空
                JSONObject station = JSONObject.parseObject(json.get("station").toString());
                JSONObject weather = JSONObject.parseObject(json.get("weather").toString());
                JSONObject wind = JSONObject.parseObject(json.get("wind").toString());
                JSONObject warn = JSONObject.parseObject(json.get("warn").toString());
                if (json.get("publish_time") == null) continue;
                String time = json.get("publish_time").toString();                   //yyyyMMddHH
                String key = "NMC:real:" + time.substring(0, 4) + time.substring(5, 7) + time.substring(8, 10) + time.substring(11, 13);
                if (station.get("code") == null) continue;
                String field = station.get("code").toString();
                JedisUtil.HASH.hset(key, field, String.valueOf(json));
                JedisUtil.KEYS.expired(key, Constant.CONSTANCE_TIME_48);              //设置key过期时间Constant.CONSTANCE_TIME_48
                //手动删除24h前的field
//            DateTime dateTime = DateTimeUtil.string2DateTime(time);
//            dateTime.minusHours(24);
//            field = dateTime.toString(DateTimeFormat.forPattern("yyyyMMddHH"));
//            if (jedis.hexists(key,field)){
//                jedis.hdel(key, field);
//            }
            }
            JedisUtil.closeJedisConn();
        }
        return true;
    }


    //添加sql具体内容
    private boolean addsql(JSONObject json ,Connection connection){
        try {
            String sqls = "insert into NMC_STATION_REAL(stationcode,stationname,otime,tt,msl,ws,wd,rh,rain,ww,dt,feeltt,comfort,aqi) " +
                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            if (json.get("station")==null||json.get("weather")==null||json.get("wind")==null||json.get("warn")==null||json.get("aqi")==null)
                return false;

            PreparedStatement ptmt = connection.prepareStatement(sqls);
            dealSQL(json , ptmt);
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    //更新sql具体内容
    private void updatesql(JSONObject json ,Connection connection, ResultSet resultSet){
        try {
            String sqls = "update NMC_STATION_REAL set stationcode=?,stationname=?,otime=?,tt=?,msl=?,ws=?,wd=?,rh=?," +
                    "rain=?,ww=?,dt=?,feeltt=?,comfort=?,aqi=? where id=?";

            PreparedStatement ptmt = connection.prepareStatement(sqls);
            dealSQL(json , ptmt);
            ptmt.setString(15, resultSet.getString("id"));
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //对具体sql的执行,添加和修改内容基本一致
    private void dealSQL(JSONObject json,PreparedStatement ptmt) throws SQLException {
        JSONObject station = JSONObject.parseObject(json.get("station").toString());
        JSONObject weather = JSONObject.parseObject(json.get("weather").toString());
        JSONObject wind = JSONObject.parseObject(json.get("wind").toString());
        JSONObject warn = JSONObject.parseObject(json.get("warn").toString());
        JSONObject aqi = JSONObject.parseObject(json.get("aqi").toString());

        if (station.get("code")==null)station.put("code" , Constant.CONSTANCE_ERROR);
        if (station.get("city")==null)station.put("city" , Constant.CONSTANCE_ERROR);
        if (json.get("publish_time")==null)json.put("publish_time" , Constant.CONSTANCE_ERROR);
        if (weather.get("temperature")==null)weather.put("temperature" , Constant.CONSTANCE_ERROR);
        if (weather.get("airpressure")==null)weather.put("airpressure" , Constant.CONSTANCE_ERROR);
        if (weather.get("humidity")==null)weather.put("humidity" , Constant.CONSTANCE_ERROR);
        if (weather.get("rain")==null)weather.put("rain" , Constant.CONSTANCE_ERROR);
        if (weather.get("info")==null)weather.put("info" , Constant.CONSTANCE_ERROR);
        if (weather.get("temperatureDiff")==null)weather.put("temperatureDiff" , Constant.CONSTANCE_ERROR);
        if (weather.get("feelst")==null)weather.put("feelst" , Constant.CONSTANCE_ERROR);
        if (weather.get("rcomfort")==null)weather.put("rcomfort" , Constant.CONSTANCE_ERROR);
        if (wind.get("speed")==null)wind.put("speed" , Constant.CONSTANCE_ERROR);
        if (wind.get("direct")==null)wind.put("direct" , Constant.CONSTANCE_ERROR);
        if (aqi.get("text")==null)aqi.put("text" , Constant.CONSTANCE_ERROR);
        ptmt.setString(1, station.get("code").toString());
        ptmt.setString(2, station.get("city").toString());
        ptmt.setString(3, json.get("publish_time").toString().substring(0,10) + " " + json.get("publish_time").toString().substring(11,13)+":00:00");
        ptmt.setString(4, weather.get("temperature").toString());
        ptmt.setString(5, weather.get("airpressure").toString());
        ptmt.setString(6, wind.get("speed").toString());
        ptmt.setString(7, wind.get("direct").toString());
        ptmt.setString(8, weather.get("humidity").toString());
        ptmt.setString(9, weather.get("rain").toString());
        ptmt.setString(10, weather.get("info").toString());
        ptmt.setString(11, weather.get("temperatureDiff").toString());
        ptmt.setString(12, weather.get("feelst").toString());
        ptmt.setString(13, weather.get("rcomfort").toString()   );
        ptmt.setString(14, aqi.get("text").toString());
    }

    public static void main(String[] args) {
        NMC_Process NMC = new NMC_CurrentCondition();
        GFSTimeManger gfsTimeManger = new GFSTimeManger();
        GFSTimeManger.setPeriodHour(Constant.PERIOD_MINUTE_15);
        gfsTimeManger.timeManger(NMC);
    }
}
