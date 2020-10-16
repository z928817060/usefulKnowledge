package com.study.usefulknowledge.使用过的设计模式.责任链;

/*

 * 销售人员，可以批准<=5%的折扣

 */
public class SalesMan extends PriceHandler {
    @Override

    public void processDiscount(float discount) {
        // TODO Auto-generated method stub
        if(discount<=0.05){
            System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
        }else{
            successor.processDiscount(discount);
        }
    }



}