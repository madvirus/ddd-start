package com.myshop.common.event;

import org.junit.Test;

import java.util.concurrent.Executors;

import static org.junit.Assert.fail;

public class AsyncEventTest {
    @Test
    public void handleAsync() throws Exception {
        Events.init(Executors.newFixedThreadPool(3));
        Events.handleAsync((TestEvent evt) -> { throw new RuntimeException(""); });

        try {
            Events.raise(new TestEvent());
        } catch (Exception e) {
            fail();
        }
    }
}
