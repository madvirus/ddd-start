package com.myshop.order.infra.repository;

import com.myshop.SpringIntTestConfig;
import com.myshop.common.jpaspec.Specification;
import com.myshop.common.jpaspec.Specs;
import com.myshop.order.command.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@Transactional
public class JpaOrderRepositoryIntTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findByOrdererId() throws Exception {
        List<Order> orders = orderRepository.findByOrdererId("user1", 0, 10);
        assertThat(orders, hasSize(2));
        assertThat(orders.get(0).getNumber().getNumber(), equalTo("ORDER-002"));
        assertThat(orders.get(1).getNumber().getNumber(), equalTo("ORDER-001"));
        assertThat(orders.get(1).getOrderLines(), hasSize(2));
    }

    @Test
    public void findById() throws Exception {
        Order order = orderRepository.findById(new OrderNo("ORDER-002"));
        assertThat(order.getOrderLines(), hasSize(1));
    }

    @Test
    public void findAllByOrdererSpec() throws Exception {
        OrdererSpec spec = new OrdererSpec("user1");
        List<Order> orders = orderRepository.findAll(spec, "number.number desc");
        assertThat(orders, hasSize(2));
        assertThat(orders.get(1).getOrderLines(), hasSize(2));
    }

    @Test
    public void findAllByCompositeSpec() throws Exception {
        LocalDateTime fromTime = LocalDateTime.of(2016, 1, 1, 0, 0, 0);
        LocalDateTime toTime = LocalDateTime.of(2016, 1, 2, 0, 0, 0);

        Specification<Order> specs = Specs.and(
                OrderSpecs.orderer("user1"),
                OrderSpecs.between(
                        Date.from(fromTime.atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(toTime.atZone(ZoneId.systemDefault()).toInstant())
                )
        );
        List<Order> orders = orderRepository.findAll(specs, "orderer.memberId.id asc", "number.number desc");
        assertThat(orders, hasSize(1));
    }

    @Test
    public void counts() throws Exception {
        LocalDateTime fromTime = LocalDateTime.of(2016, 1, 1, 0, 0, 0);
        LocalDateTime toTime = LocalDateTime.of(2016, 1, 2, 0, 0, 0);

        Specification<Order> specs = Specs.and(
                OrderSpecs.orderer("user1"),
                OrderSpecs.between(
                        Date.from(fromTime.atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(toTime.atZone(ZoneId.systemDefault()).toInstant())
                )
        );

        Long counts = orderRepository.counts(specs);
        assertThat(counts.longValue(), equalTo(1L));
    }

    @Test
    public void countsAll() throws Exception {
        Long counts = orderRepository.countsAll();
        assertThat(counts.longValue(), equalTo(3L));
    }
}
