package com.study.usefulknowledge;

/**
 * 理解：
 *      代理：使用一个类（proxy）来调用另一个类的代码，用于解决：类中对大量代码的调用
 *      动态代理：动态实现代理，相当于泛型的作用（这里给的是object），只是用反射的形式给出
 *                实现方式：
 *                          静态代理：设计一个代理类 代理被代理类（一个接口、一个代理类、一个被代理类）
 *                          动态代理：利用反射（已经设计好的）设计一个代理类 代理被代理类（如MyInvocationHandler，如果有特别需要，可以重写
 *                                      invoke方法，否则可以直接使用）
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestProxy利用反射实现动态代理面向切面的编程{
    public void Tiao() {

    }
    public static void main(String args[]) {
/**
 * 利用反射实现动态代理，面向切面的编程，就是有的方法固定有的方法动态变化
 */
        Superman man=new Superman();        //被代理类
        MyInvocationHandler mih=new MyInvocationHandler(man);
        Object obj=mih.blind(man);
        Human hu=(Human)obj;                //返回代理类对象
        hu.humanMethod();                   //代理类调用方法
        hu.info();

/**
 * 静态代理：简单的封装
  */
    }
}

interface Human{
      void humanMethod();
      void info();
}

//被代理类
class Superman implements Human{
    @Override
    public void humanMethod() {
        System.out.println("我是超人");
    }
    @Override
    public void info() {
        System.out.println("我是info");
    }
}

//代理类
class MyInvocationHandler implements InvocationHandler{
    Object obj;
    public MyInvocationHandler(Object man) {
    }
    public Object blind(Object obj){
    this.obj=obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);
}
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        HumanUtil hu=new HumanUtil();
//        hu.method1();
        Object returnVal=method.invoke(obj,args);          //代理类里，被代理类前后可以添加其他代码
//        hu.method2();
        return returnVal;
    }
}

class HumanUtil{                                      //可以替换为其他，也可以去掉
    public void method1(){
        System.out.println("=====method1=====");
    }
    public void method2(){
        System.out.println("=====method2=====");
    }
}
////代理类对象
//class MyProxy{
//    public static Object getProxyInstance(Object obj){
//        MyInvocationHandler handler=new MyInvocationHandler(obj);
//        handler.blind(obj);
//        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),handler);
//    }
//}