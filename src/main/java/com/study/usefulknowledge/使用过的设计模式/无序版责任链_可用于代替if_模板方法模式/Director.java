package com.study.usefulknowledge.使用过的设计模式.无序版责任链_可用于代替if_模板方法模式;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Director extends PriceHandler {
    @Override
    public void processDiscount(float discount) {
        if(discount<=0.25){
            System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
        }
    }
}
