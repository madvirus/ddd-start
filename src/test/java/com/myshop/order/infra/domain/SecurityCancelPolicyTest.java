package com.myshop.order.infra.domain;

import com.myshop.SecurityContextUtil;
import com.myshop.SpringIntTestConfig;
import com.myshop.member.domain.MemberId;
import com.myshop.order.command.domain.CancelPolicy;
import com.myshop.order.command.domain.Canceller;
import com.myshop.order.command.domain.Order;
import com.myshop.order.command.domain.Orderer;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
public class SecurityCancelPolicyTest {
    @Autowired
    private CancelPolicy cancelPolicy;

    @After
    public void tearDown() throws Exception {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void admin_role_has_cancellation_permission() throws Exception {
        SecurityContextUtil.setAuthentication("admin", "ROLE_ADMIN");
        assertTrue(cancelPolicy.hasCancellationPermission(createOrder(), new Canceller("admin")));
    }

    private Order createOrder() {
        Order order = mock(Order.class);
        when(order.getOrderer()).thenReturn(new Orderer(new MemberId("user1"), ""));
        return order;
    }
}
