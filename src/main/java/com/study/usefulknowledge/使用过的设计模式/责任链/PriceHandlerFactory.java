package com.study.usefulknowledge.使用过的设计模式.责任链;

public class PriceHandlerFactory {
     /*
      * 创建PriceHandler的工厂方法
      */
    public static PriceHandler createPriceHandler(){
        PriceHandler sales = new SalesMan();
        PriceHandler man = new Manager();
        PriceHandler dir = new Director();
        PriceHandler boss = new Boss();


        sales.setSuccessor(man);
        man.setSuccessor(dir);
        dir.setSuccessor(boss);

        return sales;

    }

    public static void main(String[] args) {
        System.out.println(1);
    }

}