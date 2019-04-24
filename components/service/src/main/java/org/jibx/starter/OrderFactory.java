package org.jibx.starter;

public class OrderFactory {
    public static  Order create(long number){
        Order order = new Order();
        order.setOrderNumber(number);
        return  order;
    }
}
