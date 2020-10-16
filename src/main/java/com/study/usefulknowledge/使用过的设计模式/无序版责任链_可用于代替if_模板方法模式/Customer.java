package com.study.usefulknowledge.使用过的设计模式.无序版责任链_可用于代替if_模板方法模式;
/*

 * 客户，请求折扣

 */

import java.util.List;

public class Customer {
    private PriceHandler priceHandler;
    public void setPriceHandler(PriceHandler priceHandler) {
        this.priceHandler = priceHandler;
    }
    public void requestDiscount(float discount) {
        priceHandler.processDiscount(discount);
    }
    public static void main(String[] args) {
        List<PriceHandler> list = PriceHandlerFactory.createPriceHandler();
        for (PriceHandler p:list) {                                         //这里代替无序的if
            p.processDiscount(0.5f);
        }
    }
}
