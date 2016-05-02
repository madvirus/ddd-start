package com.myshop.order.query.dao;

import com.myshop.order.query.dto.OrderView;

import java.util.List;

public interface OrderViewDao {
    List<OrderView> selectByOrderer(String ordererId);
}
