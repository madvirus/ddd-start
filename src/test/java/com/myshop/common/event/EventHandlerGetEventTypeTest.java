package com.myshop.common.event;

import com.myshop.member.domain.PasswordChangedEvent;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class EventHandlerGetEventTypeTest {

    private final PasswordChangedEvent evt = new PasswordChangedEvent("id", "newPw");

    @Test
    public void getAnonymousClass() throws Exception {
        EventHandler<?> handler = new EventHandler<PasswordChangedEvent>() {
            @Override
            public void handle(PasswordChangedEvent event) {
            }
        };
        assertThat(handler.canHandle(evt), equalTo(true));
    }

    @Test
    public void getLamdapType() throws Exception {
        EventHandler<?> handler = (PasswordChangedEvent event) -> {
        };
        assertThat(handler.canHandle(evt), equalTo(true));
    }

    @Test
    public void getClassType() throws Exception {
        PasswordChangeHandler handler = new PasswordChangeHandler();
        assertThat(handler.canHandle(evt), equalTo(true));
    }

    @Test
    public void getInnerClassType() throws Exception {
        InnerClassHandler handler = new InnerClassHandler();
        assertThat(handler.canHandle(evt), equalTo(true));
    }

    private class InnerClassHandler implements EventHandler<PasswordChangedEvent> {

        @Override
        public void handle(PasswordChangedEvent event) {
        }
    }
}
