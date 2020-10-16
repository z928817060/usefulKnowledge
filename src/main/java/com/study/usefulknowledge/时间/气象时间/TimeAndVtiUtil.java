package com.study.usefulknowledge.时间.气象时间;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Autor rongxiaokun
 * @Date 2019/6/14  16:01
 */
public class TimeAndVtiUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeAndVtiUtil.class);

    public static String get3hTime(){
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusHours(8);             //中国时间改成世界时
        dateTime = dateTime.withHourOfDay(dateTime.getHourOfDay()/3*3);
        String dateTimeString = dateTime.toString(DateTimeFormat.forPattern("yyyyMMddHH"));
        return dateTimeString;
    }
}
