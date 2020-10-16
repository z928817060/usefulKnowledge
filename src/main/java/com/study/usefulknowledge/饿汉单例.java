package com.study.usefulknowledge;

/**
 * Created by Administrator on 2018/3/14.
 */
public class 饿汉单例 {
    public static void main(String[] args) {

    }
}

//饿汉
class Ehan{
    private Ehan(){

    }
    private static Ehan ehan=new Ehan();
    public static Ehan getInstance(){
        return ehan;
    }
}
//懒汉
class SingleE1{
    private SingleE1(){

    }
    private static SingleE1 singleE1=null;
    public static SingleE1 getInstance(){
        if(singleE1==null){
            singleE1=new SingleE1();
        }
        return singleE1;
    }
}
//优化版懒汉(√)
class SingleE2{
    private SingleE2() {
    }
    private static SingleE2 instance;
    public static SingleE2 getInstance() {
        if (instance == null) {
            synchronized (SingleE2.class) {
                if (instance == null) {
                    instance = new SingleE2();
                }
            }
        }
        return instance;
    }
}
//优化版懒汉(√)
class Singleton {
    /* 私有构造方法，防止被实例化 */
    private Singleton() {
    }
    /* 此处使用一个内部类来维护单例 */
    private static class SingletonFactory {
        private static Singleton instance = new Singleton();
    }
    /* 获取实例 */
    public static Singleton getInstance() {
        return SingletonFactory.instance;
    }
    public Object readResolve() {
        return getInstance();
    }

}