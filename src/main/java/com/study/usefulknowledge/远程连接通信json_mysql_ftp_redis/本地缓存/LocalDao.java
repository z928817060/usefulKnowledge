package com.study.usefulknowledge.远程连接通信json_mysql_ftp_redis.本地缓存;

import com.alibaba.fastjson.JSONObject;
import net.xhms.data.nmcrealFC.commontool.Constant;
import net.xhms.data.nmcrealFC.commontool.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 功能:每个传入的数据对象
 * @Autor rongxiaokun
 * @Date 2018/8/1  10:02
 */
@Component
public class LocalDao implements CalculateMermory {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDao.class);
    private  JSONObject json;
    private  String time;
    private Station station;
    @Override
    public long getMemSize() {      //这里假设设置了数据个数大小
        if (json == null || json.isEmpty()){return 0;}
        return json.toString().length()*2;//todo 不清楚json的大小规定,目前按照char计算(用网页查看只有约1/2char的大小)
    }

    @Override
    public boolean isExceed() {
        return isExceed(time);
    }

    /**
     * 功能:具体设置过期时间
     * @param time  yyyyMMddHH
     * @return
     */
    public boolean isExceed(String time) {
        if (time.length() < 10)time = time + "06";                                          //针对下载日期为日情况的缓存过期设置
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
        long time_d = 0;
        try {
            time_d = Math.abs(df.parse(time).getTime() - System.currentTimeMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (time_d> Constant.PERIOD_HOUR_24)?true : false;
    }

    public  JSONObject getJson() {
        return json;
    }

    public  void setJson(JSONObject json) {
        this.json = json;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
