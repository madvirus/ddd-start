package com.myshop.integration;

import com.myshop.eventstore.api.EventEntry;
import com.myshop.eventstore.api.EventStore;
import com.myshop.integration.infra.MemoryOffsetStore;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class EventForwarderTest {
    private OffsetStore offsetStore = new MemoryOffsetStore();
    private EventStore fakeEventStore = new EventStore() {
        private List<EventEntry> events = Arrays.asList(
                createEventEntry(1L), createEventEntry(2L), createEventEntry(3L), createEventEntry(4L), createEventEntry(5L), createEventEntry(6L));

        @Override
        public void save(Object event) {}

        @Override
        public List<EventEntry> get(long offset, long limit) {
            if (offset >= events.size()) return Collections.emptyList();
            int toIdx = (int)(events.size() < offset + limit ? events.size() : offset + limit);
            int fromIdx = (int)offset;
            return events.subList(fromIdx, toIdx);
        }
    };

    private List<EventEntry> sendedEvent = new ArrayList<>();
    private EventSender fakeEventSender = event -> sendedEvent.add(event);

    private EventForwarder forwarder;

    @Before
    public void setUp() throws Exception {
        forwarder = new EventForwarder();
        forwarder.setLimitSize(4);
        forwarder.setOffsetStore(offsetStore);
        forwarder.setEventSender(fakeEventSender);
        forwarder.setEventStore(fakeEventStore);
    }

    private EventEntry createEventEntry(long id) {
        return new EventEntry(id, "java.lang.Integer", "application/json",  Long.toString(id), System.currentTimeMillis());
    }

    @Test
    public void getAndSend() throws Exception {
        forwarder.getAndSend();

        assertThat(sendedEvent, hasSize(4));

        sendedEvent.clear();
        forwarder.getAndSend();
        assertThat(sendedEvent, hasSize(2));

        sendedEvent.clear();
        forwarder.getAndSend();
        assertThat(sendedEvent, hasSize(0));
    }
}
