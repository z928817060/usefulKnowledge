package com.study.usefulknowledge.使用过的设计模式.责任链;
/*

 * 客户，请求折扣

 */

import java.util.Random;

public class Customer {
    private PriceHandler priceHandler;
    public void setPriceHandler(PriceHandler priceHandler) {
        this.priceHandler = priceHandler;
    }
    public void requestDiscount(float discount) {
        priceHandler.processDiscount(discount);
    }
    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setPriceHandler(PriceHandlerFactory.createPriceHandler());
        Random rand = new Random();
        for (int i = 1; i <= 20; i++) {
            System.out.print(i + "");
            customer.requestDiscount(rand.nextFloat());
        }
    }
}
