package com.myshop.order.command.application;

import com.myshop.common.event.Events;
import com.myshop.order.NoOrderException;
import com.myshop.order.command.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CancelOrderService {
    private OrderRepository orderRepository;
    private RefundService refundService;
    private CancelPolicy cancelPolicy;

    @Transactional
    public void cancel(OrderNo orderNo, Canceller canceller) {
        Events.handle((OrderCanceledEvent evt) -> refundService.refund(evt.getOrderNumber()));

        Order order = findOrder(orderNo);
        if (!cancelPolicy.hasCancellationPermission(order, canceller)) {
            throw new NoCancellablePermission();
        }
        order.cancel();

        //Events.reset();
    }

    private Order findOrder(OrderNo orderNo) {
        Order order = orderRepository.findById(orderNo);
        if (order == null) throw new NoOrderException();
        return order;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setRefundService(RefundService refundService) {
        this.refundService = refundService;
    }

    @Autowired
    public void setCancelPolicy(CancelPolicy cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }
}
