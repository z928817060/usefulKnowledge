package com.study.usefulknowledge.使用过的设计模式.无序版责任链_可用于代替if_模板方法模式;

import java.util.ArrayList;
import java.util.List;

public class PriceHandlerFactory {
     /*
      * 创建PriceHandler的工厂方法
      */
    public static List<PriceHandler> createPriceHandler(){
        PriceHandler sales = new SalesMan();
        PriceHandler man = new Manager();
        PriceHandler dir = new Director();
        PriceHandler boss = new Boss();
        List<PriceHandler> list = new ArrayList<PriceHandler>();
        list.add(sales);
        list.add(man);
        list.add(dir);
        list.add(boss);

//        sales.setSuccessor(man);
//        man.setSuccessor(dir);
//        dir.setSuccessor(boss);

        return list;

    }

}