package com.study.usefulknowledge.��Ϣ;

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
 * ����
 *
 * Queue���ܣ� �����ϣ�һ�����о���һ�������ȳ���FIFO�������ݽṹ
                Queue�ӿ���List��Setͬһ���𣬶��Ǽ̳���Collection�ӿڡ�LinkedListʵ����Deque�� �ڡ�
   queue���ࣺ������ PriorityQueue �� ConcurrentLinkedQueue   ������������������Ϊ��ȡ����������
              ������������* ArrayBlockingQueue ��һ��������֧�ֵ��н���С�
                     ����* LinkedBlockingQueue ��һ�������ӽڵ�֧�ֵĿ�ѡ�н���С�
                     ����* PriorityBlockingQueue ��һ�������ȼ���֧�ֵ��޽����ȼ����С�
                     ����* DelayQueue ��һ�������ȼ���֧�ֵġ�����ʱ��ĵ��ȶ��С�
                     ����* SynchronousQueue ��һ������ BlockingQueue �ӿڵļ򵥾ۼ���rendezvous�����ơ�
   ����ʹ�ã�����    add        ����һ��Ԫ��                     ����������������׳�һ��IIIegaISlabEepeplian�쳣
                 ����remove   �Ƴ������ض���ͷ����Ԫ��    �������Ϊ�գ����׳�һ��NoSuchElementException�쳣
                 ����element  ���ض���ͷ����Ԫ��             �������Ϊ�գ����׳�һ��NoSuchElementException�쳣
                 ����offer       ���һ��Ԫ�ز�����true       ��������������򷵻�false
                 ����poll         �Ƴ������ʶ���ͷ����Ԫ��    �������Ϊ�գ��򷵻�null
                 ����peek       ���ض���ͷ����Ԫ��             �������Ϊ�գ��򷵻�null
             ���̡�put         ���һ��Ԫ��                      �����������������
             ���̡�take        �Ƴ������ض���ͷ����Ԫ��     �������Ϊ�գ�������
 */



public class BlockingQueueTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockingQueueTest.class);

    public static class Basket{
        /**
         * �����������֣�����������ά����Ҫ�ֶ�д��������ڵ���Ϣ��
         */
        /*******************************************************************************************************
         */
        static Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String t1, String t2) {          //���ƵıȽ�����
                int num1 = Integer.parseInt(t1);
                int num2 = Integer.parseInt(t2);
                return num1>num2?1:(num1<num2?-1:0);
            }
        };
        BlockingQueue<String> basket = new PriorityBlockingQueue<String>(10,comparator);
        /********************************************************************************************************
         */
        //����ƻ��
        public void produce(String product){
            try {
                basket.put(product);
//                basket.put("A apple");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //����ƻ��
        public String consume(){
            String apple = null;
            try {
                apple = String.valueOf(basket.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return apple;
        }
        //��ȡ����
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
                        System.out.println("������׼������������");
                        basket.produce(String.valueOf(num));
                        System.out.println("������������ϣ�����");
                        System.out.println("�������ƻ��������  "+basket.getAppleNum());
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
                        System.out.println("�����߿�ʼ���ѣ�����");
                        basket.consume();
                        System.out.println("������������ϣ�����");
                        System.out.println("���Ѻ�Ĳ�Ʒ������ " + basket.getAppleNum());
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
