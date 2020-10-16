package com.study.usefulknowledge.使用过的设计模式.无序版责任链_可用于代替if_模板方法模式;

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