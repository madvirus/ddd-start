package com.myshop.order.query.dao;

import com.myshop.common.jpaspec.Specification;
import com.myshop.order.query.dto.OrderSummary;

import java.util.List;

public interface OrderSummaryDao {
    List<OrderSummary> selectByOrderer(String ordererId);
    List<OrderSummary> select(Specification<OrderSummary> spec, int firstRow, int maxResults);
    long counts(Specification<OrderSummary> spec);
}
