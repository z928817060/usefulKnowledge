package com.study.usefulknowledge.消息;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Autor rongxiaokun
 * @Date 2019/9/11  14:13
 */

/**
 * 队列
 *
 * Queue介绍： 基本上，一个队列就是一个先入先出（FIFO）的数据结构
                Queue接口与List、Set同一级别，都是继承了Collection接口。LinkedList实现了Deque接 口。
   queue分类：阻塞： PriorityQueue 和 ConcurrentLinkedQueue   （阻塞：满了增或者为空取都会阻塞）
              非阻塞：　　* ArrayBlockingQueue ：一个由数组支持的有界队列。
                     　　* LinkedBlockingQueue ：一个由链接节点支持的可选有界队列。
                     　　* PriorityBlockingQueue ：一个由优先级堆支持的无界优先级队列。
                     　　* DelayQueue ：一个由优先级堆支持的、基于时间的调度队列。
                     　　* SynchronousQueue ：一个利用 BlockingQueue 接口的简单聚集（rendezvous）机制。
   基本使用：　　    add        增加一个元索                     如果队列已满，则抛出一个IIIegaISlabEepeplian异常
                 　　remove   移除并返回队列头部的元素    如果队列为空，则抛出一个NoSuchElementException异常
                 　　element  返回队列头部的元素             如果队列为空，则抛出一个NoSuchElementException异常
                 　　offer       添加一个元素并返回true       如果队列已满，则返回false
                 　　poll         移除并返问队列头部的元素    如果队列为空，则返回null
                 　　peek       返回队列头部的元素             如果队列为空，则返回null
             　√　put         添加一个元素                      如果队列满，则阻塞
             　√　take        移除并返回队列头部的元素     如果队列为空，则阻塞
 */



public class BlockingQueueTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockingQueueTest.class);

    public static class Basket{
        /**
         * 设置容器部分，容器的清理维护需要手动写（清理过期的消息）
         */
        /*******************************************************************************************************
         */
        static Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {          //定制的比较排序
                int num1 = Integer.parseInt(t1);
                int num2 = Integer.parseInt(t2);
                return num1>num2?1:(num1<num2?-1:0);
            }
        };
        BlockingQueue<String> basket = new PriorityBlockingQueue<String>(10,comparator);
        /********************************************************************************************************
         */
        //放入苹果
        public void produce(String product){
            try {
                basket.put(product);
//                basket.put("A apple");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //消费苹果
        public String consume(){
            String apple = null;
            try {
                apple = String.valueOf(basket.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return apple;
        }
        //获取个数
        public int getAppleNum(){
            return basket.size();
        }
    }

    public static void testBasket(){
        final Basket basket = new Basket();
        class Producer implements Runnable{
            @Override
            public void run() {
                int num = 0;
                try {
                    while (num<5){
                        num++;
                        System.out.println("生产者准备生产！！！");
                        basket.produce(String.valueOf(num));
                        System.out.println("生产者生产完毕！！！");
                        System.out.println("生产后的苹果个数：  "+basket.getAppleNum());
                        Thread.sleep(300);
                    }
                    for (int i = 0; i < 5; i++) {
                        System.out.println(basket.basket.take());
                    }

                }catch (Exception e){

                }
            }
        }
        class Consumer implements Runnable{
            @Override
            public void run() {
                try {
                    while (true){
                        System.out.println("消费者开始消费！！！");
                        basket.consume();
                        System.out.println("消费者消费完毕！！！");
                        System.out.println("消费后的产品个数： " + basket.getAppleNum());
                        Thread.sleep(300);
                    }
                }catch (Exception e ){

                }
            }
        }

        ExecutorService service = Executors.newCachedThreadPool();
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        service.submit(producer);
//        service.submit(consumer);
        try {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch (Exception e ){

        }
        service.shutdownNow();
    }

    public static void main(String[] args) {
        BlockingQueueTest.testBasket();
    }

}
