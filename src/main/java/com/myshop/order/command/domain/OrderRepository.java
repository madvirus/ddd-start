package com.myshop.order.command.domain;

import com.myshop.common.jpaspec.Specification;

import java.util.List;

public interface OrderRepository {
    Order findById(OrderNo id);

    Order findByIdOptimisticLockMode(OrderNo id);

    List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize);

    void save(Order order);

    void remove(Order order);

    List<Order> findAll(Specification<Order> spec, String ... orders);

    Long counts(Specification<Order> spec);

    Long countsAll();

    OrderNo nextOrderNo();
}
