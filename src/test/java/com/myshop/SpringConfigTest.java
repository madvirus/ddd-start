package com.myshop;

import com.myshop.common.event.EventStoreHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ShopApplication.class)
public class SpringConfigTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void eventStoreHandler_is_not_applied_by_EventAOP() throws Exception {
        Object bean = applicationContext.getBean("eventStoreHandler");
        assertThat(bean, notNullValue());
        assertThat(bean, instanceOf(EventStoreHandler.class));
    }
}
