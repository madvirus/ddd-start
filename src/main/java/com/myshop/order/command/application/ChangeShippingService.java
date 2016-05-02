package com.myshop.order.command.application;

import com.myshop.order.NoOrderException;
import com.myshop.order.command.domain.Order;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeShippingService {
    private OrderRepository orderRepository;

    @Transactional
    public void changeShipping(ChangeShippingRequest changeReq) {
        Order order = orderRepository.findById(new OrderNo(changeReq.getNumber()));
        checkNoOrder(order);
        order.changeShippingInfo(changeReq.getShippingInfo());
    }

    private void checkNoOrder(Order order) {
        if (order == null)
            throw new NoOrderException();
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
