package com.myshop.order.infra.dao;

import com.myshop.ShopApplication;
import com.myshop.SpringIntTestConfig;
import com.myshop.order.query.dao.OrderSummaryDao;
import com.myshop.order.query.dao.OrderSummarySpecs;
import com.myshop.order.query.dto.OrderSummary;
import com.myshop.order.query.dto.OrderSummary_;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@SpringApplicationConfiguration(ShopApplication.class)
public class JpaOrderSummaryDaoIntTest {
    @Autowired
    private OrderSummaryDao orderViewDao;

    @Test
    public void selectByOrderer() throws Exception {
        List<OrderSummary> orders = orderViewDao.selectByOrderer("user1");
        assertThat(orders, hasSize(2));
        OrderSummary orderView = orders.get(0);
        assertThat(orderView.getNumber(), equalTo("ORDER-002"));
        assertThat(orderView.getVersion(), equalTo(2L));
        assertThat(orderView.getOrdererId(), equalTo("user1"));
        assertThat(orderView.getOrdererName(), equalTo("사용자1"));
        assertThat(orderView.getProductId(), equalTo("prod-001"));
        assertThat(orderView.getProductName(), equalTo("라즈베리파이3 모델B"));
        assertThat(orderView.getTotalAmounts(), equalTo(5000));
    }

    @Test
    public void countsBySpec() throws Exception {
        assertThat(orderViewDao.counts(null), equalTo(3L));
        assertThat(orderViewDao.counts(OrderSummarySpecs.ordererId("user1")), equalTo(2L));
        assertThat(orderViewDao.counts(OrderSummarySpecs.ordererId("user2")), equalTo(1L));
    }

    @Test
    public void selectBySpec() throws Exception {
        List<OrderSummary> allResults = orderViewDao.select(null, 0, 10);
        assertThat(allResults, hasSize(3));

        List<OrderSummary> user1Results = orderViewDao.select(OrderSummarySpecs.ordererId("user1"), 0, 10);
        assertThat(user1Results, hasSize(2));

        List<OrderSummary> user2Results = orderViewDao.select(OrderSummarySpecs.ordererId("user2"), 0, 10);
        assertThat(user2Results, hasSize(1));
    }
}
