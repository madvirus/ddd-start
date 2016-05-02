package com.myshop.lock;

import com.myshop.SpringIntTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
public class LockManagerBasicTest {
    @Autowired
    private LockManager lockManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() throws Exception {
        jdbcTemplate.update("truncate table locks");
    }

    @Test
    public void tryLock() throws Exception {
        LockId lock = lockManager.tryLock("type1", "id1");

        try {
            lockManager.tryLock("type1", "id1");
            fail();
        } catch (AlreadyLockedException e) {
        }

        lockManager.releaseLock(lock);

        int count = jdbcTemplate.queryForObject(
                "select count(*) from locks where type = ? and id = ?",
                Integer.class,
                "type1", "id1").intValue();
        assertThat(count, equalTo(0));
    }

    @Test
    public void when_lockExpired_then_other_get_lock() throws Exception {
        jdbcTemplate.update("insert into locks values (?, ?, ?, ?)",
                "type2", "id2", "lockid", new Timestamp(System.currentTimeMillis() - 1000));

        LockId lock = null;
        try {
            lock = lockManager.tryLock("type2", "id2");
        } catch (LockException e) {
            fail();
        }
        lockManager.releaseLock(lock);
    }

    @Test
    public void checkLock() throws Exception {


    }

    @Test
    public void extendExpiration() throws Exception {


    }
}
