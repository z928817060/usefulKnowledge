package com.study.usefulknowledge.时间.定时执行任务.quartz定时器;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule;
import static org.quartz.DateBuilder.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest {

    public static void main(String[] args) {
        try {
            //创建scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //定义一个Trigger(可以替换成更为复杂的定时形式,例如:每周一周二,每个月的15号...使用CronTrigger就方便书写)
//            Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义name/group
//                    .startNow()//一旦加入scheduler，立即生效
//                    .withSchedule(simpleSchedule() //使用SimpleTrigger
//                            .withIntervalInSeconds(1) //每隔一秒执行一次
//                            .repeatForever()) //一直执行，奔腾到老不停歇
//                    .build();
            Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义name/group
                    .startNow()//一旦加入scheduler，立即生效
                    .withSchedule(QuartzTest.selectTrigger("CronTrigger")) //一直执行，奔腾到老不停歇
                    .build();
            //定义一个JobDetail
            JobDetail job = newJob(HelloQuartz.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                    .withIdentity("job1", "group1") //定义name/group
                    .usingJobData("name", "quartz") //定义属性
                    .build();
            //加入这个调度
            scheduler.scheduleJob(job, trigger);
            //启动之
            scheduler.start();
            //运行一段时间后关闭
            Thread.sleep(10000);
            scheduler.shutdown(true);           //关闭该池
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ScheduleBuilder selectTrigger(String type){
        ScheduleBuilder scheduleBuilder = null;
        /**
         * 指定从某一个时间开始，以一定的时间间隔（单位是毫秒）执行的任务。
         它适合的任务类似于：9:00 开始，每隔1小时，执行一次。
         它的属性有：
         repeatInterval 重复间隔
         repeatCount 重复次数。实际执行次数是 repeatCount+1。因为在startTime的时候一定会执行一次。** 下面有关repeatCount 属性的都是同理。　**
         */
        if (type.equals("SimpleTrigger")){
            scheduleBuilder = simpleSchedule()
                    .withIntervalInHours(1) //每小时执行一次
                    .repeatForever(); //次数不限

//            scheduleBuilder = simpleSchedule()
//                    .withIntervalInMinutes(1) //每分钟执行一次
//                    .withRepeatCount(10); //次数为10次
//
        }
        /**
         * 类似于SimpleTrigger，指定从某一个时间开始，以一定的时间间隔执行的任务。 但是不同的是SimpleTrigger指定的时间间隔为毫秒，没办法指定每隔一个月执行一次（每月的时间间隔不是固定值），而CalendarIntervalTrigger支持的间隔单位有秒，分钟，小时，天，月，年，星期。
         相较于SimpleTrigger有两个优势：1、更方便，比如每隔1小时执行，你不用自己去计算1小时等于多少毫秒。 2、支持不是固定长度的间隔，比如间隔为月和年。但劣势是精度只能到秒。
         它适合的任务类似于：9:00 开始执行，并且以后每周 9:00 执行一次
         它的属性有:
         interval 执行间隔
         intervalUnit 执行间隔的单位（秒，分钟，小时，天，月，年，星期）
         */
        if (type.equals("CalendarIntervalTrigger")){
            scheduleBuilder = calendarIntervalSchedule()
                    .withIntervalInDays(1); //每天执行一次
//            scheduleBuilder = calendarIntervalSchedule()
//                    .withIntervalInWeeks(1); //每周执行一次
        }
        /**
         * 指定每天的某个时间段内，以一定的时间间隔执行任务。并且它可以支持指定星期。
         它适合的任务类似于：指定每天9:00 至 18:00 ，每隔70秒执行一次，并且只要周一至周五执行。
         它的属性有:
         startTimeOfDay 每天开始时间
         endTimeOfDay 每天结束时间
         daysOfWeek 需要执行的星期
         interval 执行间隔
         intervalUnit 执行间隔的单位（秒，分钟，小时，天，月，年，星期）
         repeatCount 重复次数
         */
        if (type.equals("DailyTimeIntervalTrigger")){
            scheduleBuilder = dailyTimeIntervalSchedule()
                    .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0)) //第天9：00开始
                    .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(16, 0)) //16：00 结束
                    .onDaysOfTheWeek(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY) //周一至周五执行(org.quartz.DateBuilder)
                    .withIntervalInHours(1) //每间隔1小时执行一次
                    .withRepeatCount(100); //最多重复100次（实际执行100+1次）
//            scheduleBuilder = dailyTimeIntervalSchedule()
//                    .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0)) //第天9：00开始
//                    .endingDailyAfterCount(10) //每天执行10次，这个方法实际上根据 startTimeOfDay+interval*count 算出 endTimeOfDay
//                    .onDaysOfTheWeek(MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY) //周一至周五执行
//                    .withIntervalInHours(1); //每间隔1小时执行一次
        }
        /**
         * 适合于更复杂的任务，它支持类型于Linux Cron的语法（并且更强大）。基本上它覆盖了以上三个Trigger的绝大部分能力（但不是全部）—— 当然，也更难理解。
         它适合的任务类似于：每天0:00,9:00,18:00各执行一次。
         它的属性只有:
         Cron表达式。但这个表示式本身就够复杂了。下面会有说明:"Cron表达式.log"。
         */
        if (type.equals("CronTrigger")){
//            scheduleBuilder = cronSchedule("0 0/2 8-17 * * ?"); // 每天8:00-17:00，每隔2分钟执行一次
            scheduleBuilder = cronSchedule("0/1 * * * * ?"); // 每天8:00-17:00，每隔2分钟执行一次
//            scheduleBuilder = cronSchedule("0 30 9 ? * MON"); // 每周一，9:30执行一次
//            scheduleBuilder = weeklyOnDayAndHourAndMinute(MONDAY,9, 30); //等同于 0 30 9 ? * MON
        }

        return scheduleBuilder;
    }
}