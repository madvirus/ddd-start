package com.myshop.common.event;

import com.myshop.member.domain.PasswordChangedEvent;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EventStoreHandlerTest {
    @Test
    public void canHandle() throws Exception {
        EventStoreHandler storeHandler = new EventStoreHandler();
        assertTrue(storeHandler.canHandle(new Object()));
        assertTrue(storeHandler.canHandle("string"));
        assertTrue(storeHandler.canHandle(new PasswordChangedEvent("id", "newPw")));
    }
}
