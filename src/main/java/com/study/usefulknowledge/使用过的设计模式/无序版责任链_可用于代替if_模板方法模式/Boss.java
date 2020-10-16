package com.study.usefulknowledge.使用过的设计模式.无序版责任链_可用于代替if_模板方法模式;

/*

 * 老板，可以批准50%以内的折扣

 * 折扣超出50%，就拒绝申请

 */

public class Boss extends PriceHandler {
    @Override
    public void processDiscount(float discount) {
        // TODO Auto-generated method stub
        if(discount<=0.50){
            System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
        }
    }
}