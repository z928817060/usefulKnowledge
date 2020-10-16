package com.study.usefulknowledge;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/3/14.
 */
public class reflex反射 {
    public static void main(String[] args) {
        Test0 test0=new Test0();
        //属性
        try {
            Field field1=test0.getClass().getDeclaredField("field1");
            field1.setAccessible(true);
            String context = (String)field1.get(test0);     //获取属性值
            field1.set(test0,"new属性");                    //修改属性值
        } catch (Exception e) {
            e.printStackTrace();
        }
        //方法
        try {
            Method method1=test0.getClass().getDeclaredMethod("fun1");
            method1.setAccessible(true);
            method1.invoke(test0);

            Method method2=test0.getClass().getDeclaredMethod("fun2");
            method2.setAccessible(true);
            method2.invoke(test0,"new参数");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class Test0 {
    private String field1="private属性1";
    public String field2="public属性";
    public void fun(){
        System.out.println("fun0");
    }
    private void fun1(){
        System.out.println("fun1");
    }
    private void fun2(String para){
        System.out.println("privatefun2");
    }
}