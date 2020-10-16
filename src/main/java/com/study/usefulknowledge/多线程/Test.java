package com.study.usefulknowledge.多线程;

import jxl.write.DateTime;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 说明:  频繁地创建,删除线程,会消耗系统资源,因此使用线程池,将线程一直保持一定的数量
 * 功能: 利用  线程池  执行多线程任务
 *       1.ThreadPoolExecutor的配置,核心线程数常常选择(cpu+1(cpu密集)或者2*cpu(io密集))
 *      doJob()用于  下载\文件读取等一些阻塞  可能卡死的地方,对任务进行放弃或者重启策略
 */

public class 线程池 {
    public static void main(String[] args) throws InterruptedException {
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();//对无法加入线程池的处理方式
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(10) ,handler);
        //5:核心线程数,10:最大线程数,200:200ms判断没有工作的线程就删除(保证闲着的时候线程为5),10:阻塞的缓冲任务队列
        CountDownLatch cdLatch = new CountDownLatch(3);                        //可以用于线程计数,能够达到 1.各个线程结果都出来再进行下一步的目的
                                                           //配合await()使用 2.将线程任务加入(线程数的)判断
        List<String> list = new ArrayList<>();
        list.add("start");
        for(int i=0;i<15;i++){
            MyTask myTask = new MyTask(list ,  cdLatch);
            executor.execute(myTask);                     //加入任务

            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                    executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());    //常用打印部分
//            executor.remove(myTask);
        }
        executor.shutdown();                        //停止往线程池加入线程
//        executor.shutdownNow();                   //停止所有的线程
        System.out.println("我关闭了哦");
        try {
            cdLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread.sleep(10000);
        System.out.println("队列中等待执行的任务数目:"+executor.getQueue().size());
    }
}

/**
 * 需要执行的任务
 */
class MyTask implements Runnable {
    private List<String> taskNum;
    CountDownLatch cdLatch;
    public MyTask(List<String> num ,CountDownLatch cdLatch) {
        this.taskNum = num;
        this.cdLatch = cdLatch;
    }

    @Override
    public void run() {
        synchronized (taskNum) {
            System.out.println("正在执行task "+taskNum);
            taskNum.add(String.valueOf(taskNum.size()));
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            System.out.println("task " + taskNum + "执行完毕");
        }
        for (int i = 0; i < 5; i++) {
            cdLatch.countDown();
        }
    }

    /**
     * 利用线程池监控阻塞式的程序
     */
    public void doJob() {
        List<String> stationCode = JSONUtil.getStationCode();
        int num = 800;                                          ///////////////////////////////////////////  i < stationCode.size()   500 800
        System.out.println("开始抓取数据.."+this.getClass().getSimpleName()+"----------"+new DateTime().toString(DateTimeFormat.forPattern("yyyyMMddHHmmss")));

        for (int i = 0; i < stationCode.size(); i+=num) {
//主要任务
            List<JSONObject> jsonArray = new ArrayList<>();
            ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 1000, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(num));
            key:    for (int j = 0; j < num ; j++) {
                if ((i + j )>= stationCode.size()){break key;}       //防止数组越界
                String station = stationCode.get(i + j );
                InJsonThread myTask = new InJsonThread(jsonArray , this , station );
                executor.execute(myTask);
            }
//可能阻塞部分的时间\线程监控
            for (int j = 0; j < 120; j++) {     //单个循环(800个数据)下载超过10min,认为放弃继续下载 5s*120   针对问题:抓取数据可能卡死,如何对单个抓取做
                if (executor.getQueue().size()<1){
                    try {
                        Thread.sleep(8000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }else {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            executor.shutdownNow();
        }
        System.out.println("录入缓存和数据库完成.."+this.getClass().getSimpleName()+" "+monitorService.getMonitorInfoBean().toString());
        System.gc();
    }

}