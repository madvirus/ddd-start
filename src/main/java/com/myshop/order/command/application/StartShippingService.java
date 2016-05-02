package com.myshop.order.command.application;

import com.myshop.order.command.domain.Order;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.myshop.order.command.application.CheckOrder.checkNoOrder;

@Service
public class StartShippingService {
    private OrderRepository orderRepository;

    @Transactional
    public void startShipping(StartShippingRequest req) {
        Order order = orderRepository.findById(new OrderNo(req.getOrderNumber()));
        checkNoOrder(order);
        if (!order.matchVersion(req.getVersion())) {
//            throw new VersionConflictException();
            throw new OptimisticLockingFailureException("version conflict");
        }
        order.startShipping();
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
