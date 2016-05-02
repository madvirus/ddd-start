package com.myshop.order.command.application;

import com.myshop.order.NoOrderException;
import com.myshop.order.command.domain.Order;

public interface CheckOrder {
    static void checkNoOrder(Order order) {
        if (order == null)
            throw new NoOrderException();
    }
}
