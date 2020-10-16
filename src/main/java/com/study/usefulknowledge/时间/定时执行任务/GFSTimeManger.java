package com.study.usefulknowledge.时间.定时执行任务;

//import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by wingsby on 2018/4/16.
 */
public class GFSTimeManger {
//    static final Logger logger = Logger.getLogger(GFSTimeManger.class);

    public static final int TIME_PER_DAY = 0;
    public static final int TIME_PER_WEEK = 1;
    public static final int TIME_PER_MONTH = 2;
    public static final int UNFIXED_TIME = 9;

    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000l;
    private static long PERIOD_HOUR = 60 * 60 * 1000l;
    private static final long PERIOD_WEEK = 7 * 24 * 60 * 60 * 1000l;
    private static final long tolerance = 5 * 60 * 1000l;
//    private static final long testp = 61 * 1000l;

    public GFSTimeManger() {
    }

    //dates:DDHHMM
    public GFSTimeManger(int mode, String[] dates, final TimeMangerJob timeMangerJob) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int cyear = calendar.get(Calendar.YEAR);
                int cmonth = calendar.get(Calendar.MONTH) + 1;
                int cday = calendar.get(Calendar.DAY_OF_MONTH);
                int chour = calendar.get(Calendar.HOUR_OF_DAY);
                for (String str : dates) {
                    int DAY = Integer.valueOf(str.substring(0, 2));
                    int hour = Integer.valueOf(str.substring(2, 4));
                    int minute = Integer.valueOf(str.substring(4, 6));
                    switch (mode) {
                        case TIME_PER_DAY:
                            Calendar tinstance = Calendar.getInstance();
                            tinstance.set(Calendar.HOUR_OF_DAY, hour);
                            tinstance.set(Calendar.MINUTE, minute);
                            if (tinstance.getTimeInMillis() - calendar.getTimeInMillis() < tolerance &&
                                    tinstance.getTimeInMillis() - calendar.getTimeInMillis() >= -1 * tolerance) {
                                timeMangerJob.doJob();
                            }
                            break;
                        case TIME_PER_WEEK:
                            break;
                        default:
                    }
                }
            }
        };
        Timer timer = new Timer();
        String str = dates[0];
        int DAY = Integer.valueOf(str.substring(0, 2));
        int hour = Integer.valueOf(str.substring(2, 4));
        int minute = Integer.valueOf(str.substring(4, 6));
        Calendar tcalendar = Calendar.getInstance();
        tcalendar.set(Calendar.HOUR_OF_DAY, hour);
        tcalendar.set(Calendar.MINUTE, minute);
        timer.scheduleAtFixedRate(timerTask, tcalendar.getTime(), PERIOD_HOUR);
//        timer.schedule();
    }

    /**
     * 简单的定时任务
     * @param timeMangerJob
     * 注意:  scheduleAtFixedRate(任务,开始执行的时间,执行时间间隔)
     */
    public void timeManager(final TimeMangerJob timeMangerJob) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timeMangerJob.doJob();
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, Calendar.getInstance().getTime(), PERIOD_HOUR);        //按照时间智能执行:比如程序运行了8min,间隔10min,则程序单元时间为10min(尽肯能保证两程序开始时候的10min一次)
//        timer.schedule();                                                                         //按照等间隔时间执行:程序运行8min,间隔10min,则程序运行完等10min再运行(尽可能保证一个结束和另一个开始间差10min)
    }
    /**
     * ScheduledExecutorService是从Java SE5的java.util.concurrent里，做为并发工具类被引进的，这是最理想的定时任务实现方式。
     * 相比于上两个方法，它有以下好处：
     * 1>相比于Timer的单线程，它是通过线程池的方式来执行任务的
     * 2>可以很灵活的去设定第一次执行任务delay时间
     * 3>提供了良好的约定，以便设定执行的时间间隔
     * 下面是实现代码，我们通过ScheduledExecutorService#scheduleAtFixedRate展示这个例子，通过代码里参数的控制，首次执行加了delay时间。
     */
    public void timeManager1(final TimeMangerJob timeMangerJob){
        Runnable runnable = new Runnable() {
            public void run() {
                // task to run goes here
                timeMangerJob.doJob();
//                System.out.println("Hello !!");
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 10, 1, TimeUnit.SECONDS);
    }

    public static long getPeriodHour() {
        return PERIOD_HOUR;
    }

    public static void setPeriodHour(long periodHour) {
        PERIOD_HOUR = periodHour;
    }
}

