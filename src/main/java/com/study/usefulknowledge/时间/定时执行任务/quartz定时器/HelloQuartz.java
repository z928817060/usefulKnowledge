package com.study.usefulknowledge.时间.定时执行任务.quartz定时器;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloQuartz implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        JobDetail detail = context.getJobDetail();
//        String name = detail.getJobDataMap().getString("name");
//        System.out.println("say hello to " + name + " at " + new Date());
        // TODO: 2018/9/7  dojob
        System.out.println("test");
    }
}