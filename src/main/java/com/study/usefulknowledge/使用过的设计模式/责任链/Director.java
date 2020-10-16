package com.study.usefulknowledge.使用过的设计模式.责任链;

/**
 * Created by Administrator on 2018/6/27.
 */
public class Director extends PriceHandler {
    @Override
    public void processDiscount(float discount) {
        if(discount<=0.25){
            System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
        }else{
            successor.processDiscount(discount);
        }
    }
}
