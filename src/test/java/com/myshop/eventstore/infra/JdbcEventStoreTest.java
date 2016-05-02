package com.myshop.eventstore.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myshop.SpringIntTestConfig;
import com.myshop.eventstore.api.EventStore;
import com.myshop.eventstore.api.EventEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringIntTestConfig
@RunWith(SpringJUnit4ClassRunner.class)
public class JdbcEventStoreTest {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void save() throws Exception {
        eventStore.save(new SampleEvent("name", 10));
        String payload = jdbcTemplate.queryForObject(
                "select payload from evententry order by id desc limit 0, 1", String.class);

        SampleEvent savedPayload = objectMapper.readValue(payload, SampleEvent.class);
        assertThat(savedPayload.getName(), equalTo("name"));
        assertThat(savedPayload.getValue(), equalTo(10));
    }

    @Test
    public void get() throws Exception {
        List<EventEntry> entries = eventStore.get(1, 2);
        SampleEvent event1 = objectMapper.readValue(entries.get(0).getPayload(), SampleEvent.class);
        assertThat(event1.getName(), equalTo("name2"));
        assertThat(event1.getValue(), equalTo(12));

        SampleEvent event2 = objectMapper.readValue(entries.get(1).getPayload(), SampleEvent.class);
        assertThat(event2.getName(), equalTo("name3"));
        assertThat(event2.getValue(), equalTo(13));
    }

    //    SampleEvent savedPayload = mapper.readValue(savedEntry.getPayload(), SampleEvent.class);
//    assertThat(savedPayload.getName(), equalTo(event.getName()));
//    assertThat(savedPayload.getValue(), equalTo(event.getValue()));

}
