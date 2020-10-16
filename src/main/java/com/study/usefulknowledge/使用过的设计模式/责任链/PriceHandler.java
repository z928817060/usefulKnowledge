package com.study.usefulknowledge.使用过的设计模式.责任链;

/*

 * 价格处理人，负责处理客户折扣申请

 */

public abstract class PriceHandler {
     /*
      * 直接后继，用于传递请求
      */
    protected PriceHandler successor;

    public void setSuccessor(PriceHandler successor) {
        this.successor = successor;
    }
    public abstract void processDiscount(float discount);
}