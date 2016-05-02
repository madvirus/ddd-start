package com.myshop.common.event;

import com.myshop.SpringIntTestConfig;
import com.myshop.order.command.application.CancelOrderService;
import com.myshop.order.command.domain.Canceller;
import com.myshop.order.command.domain.OrderCanceledEvent;
import com.myshop.order.command.domain.OrderNo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
@DirtiesContext
public class EventStoreHandlerAspectTest {
    @Autowired
    private CancelOrderService cancelOrderService;

    private AtomicBoolean handled = new AtomicBoolean(false);

    @Autowired
    private ApplicationContext context;
    private EventStoreHandler stubHandler;

    private AtomicReference<Object> handledEvent = new AtomicReference<>();

    @Before
    public void setUp() throws Exception {
        stubHandler = new EventStoreHandler() {
            @Override
            public void handle(Object event) {
                handledEvent.set(event);
            }
        };
        context.getBean("eventStoreHandlerAspect", EventStoreHandlerAspect.class).setEventStoreHandler(stubHandler);
        Events.handle((TestEvent event) -> {handled.set(true);});
    }

    @Test
    public void cancelService() throws Exception {
        cancelOrderService.cancel(new OrderNo("ORDER-001"), new Canceller("user1"));

        Object capturedEvent = handledEvent.get();
        assertThat(capturedEvent, instanceOf(OrderCanceledEvent.class));
    }

}
