package com.study.usefulknowledge.时间;

import jxl.write.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Autor rongxiaokun
 * @Date 2018/7/25  9:14
 */
public class TimeTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTest.class);

    public static void main(String[] args) {
        /**
         * 注意，一定要写  dateTime1 = ，只有with没效果
         */
        //时间转化
        DateTime dateTime = new DateTime();//2018072510                                     //当前时间
        DateTime dateTime1 = dateTime.withDate(2018, 10, 25);                               //设置时间的年月日
        DateTime dateTime2 = dateTime1.withHourOfDay(15);                                   //设置时间的h
        DateTime dateTime3 = dateTime2.minusHours(1);                                       //设置时间,向前1h
        System.out.println(dateTime2.toString("yyyyMMddHH"));                               //直接打印       System.out.println(dateTime2.toString(DateTimeFormat.forPattern("yyyyMMddHH")));    //打印标准时间
        dateTime.getMillis();                                                               //转毫秒时间

        //时间转化
        Calendar calendar = Calendar.getInstance();                                         //当前时间
        int cyear = calendar.get(Calendar.YEAR);                                            //时间获取
        int cmonth = calendar.get(Calendar.MONTH) + 1;                                      //月份要+1
        int cday = calendar.get(Calendar.DAY_OF_MONTH);
        int chour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(Calendar.HOUR_OF_DAY, 2);                                              //设置时间
        calendar.set(Calendar.MINUTE, 3);
        calendar.setTimeInMillis(calendar.getTimeInMillis()-60*1000);                       //设置时间,向前1min,按照毫秒计算
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");         //打印标准时间
        System.out.println(df.format(calendar.getTime())); //2018-06-06 08:25:06
        try {
            System.out.println(df.parse("2018-02-05 11:11:10").getTime());          //解固定格式时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
