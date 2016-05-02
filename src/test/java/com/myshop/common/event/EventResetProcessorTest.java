package com.myshop.common.event;

import com.myshop.SpringIntTestConfig;
import com.myshop.eventstore.api.EventStore;
import com.myshop.member.application.BlockMemberService;
import com.myshop.order.command.application.CancelOrderService;
import com.myshop.order.command.domain.Canceller;
import com.myshop.order.command.domain.OrderNo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@DirtiesContext
public class EventResetProcessorTest {
    @Autowired
    private BlockMemberService blockMemberService;
    @Autowired
    private CancelOrderService cancelOrderService;

    private AtomicBoolean handled = new AtomicBoolean(false);

    @Autowired
    private ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context.getBean("eventStoreHandler", EventStoreHandler.class).setEventStore(mock(EventStore.class));
        Events.handle((TestEvent event) -> {handled.set(true);});
    }

    @Test
    public void blockService() throws Exception {
        initSecurityContextForBlockMember();
        blockMemberService.block("user1");
        assertNoEventHandler();
    }

    @Test
    public void cancelService() throws Exception {
        cancelOrderService.cancel(new OrderNo("ORDER-001"), new Canceller("user1"));
        assertNoEventHandler();
    }

    private void assertNoEventHandler() {
        Events.raise(new TestEvent());
        assertThat(handled.get(), equalTo(false));
    }

    private void initSecurityContextForBlockMember() {
        SecurityContextHolder.setContext(new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new UsernamePasswordAuthenticationToken("admin", "admin", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        });
    }
}
