package com.study.usefulknowledge.使用过的设计模式.责任链;

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
        }else{
            System.out.format("%s拒绝了折扣：%.2f%n", this.getClass().getName(), discount);
        }
    }
}