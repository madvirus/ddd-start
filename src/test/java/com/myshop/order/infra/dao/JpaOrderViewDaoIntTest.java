package com.myshop.order.infra.dao;

import com.myshop.SpringIntTestConfig;
import com.myshop.catalog.domain.product.ProductId;
import com.myshop.member.domain.MemberId;
import com.myshop.order.query.dao.OrderViewDao;
import com.myshop.order.query.dto.OrderView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
public class JpaOrderViewDaoIntTest {
    @Autowired
    private OrderViewDao orderViewDao;

    @Test
    public void select() throws Exception {
        List<OrderView> orders = orderViewDao.selectByOrderer("user1");
        assertThat(orders, hasSize(2));
        OrderView orderView = orders.get(0);
        assertThat(orderView.getMember().getId(), equalTo(new MemberId("user1")));
        assertThat(orderView.getProduct().getId(), equalTo(new ProductId("prod-001")));

    }
}
