package com.study.usefulknowledge;
/***
 * @TimeCheck 时间截断
 * **/
public class TimeCheck时间截断 {
    public static void main (String[] args) {


        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        System.out.println("循环用时:" + (end - start) + "ms");
    }
}
