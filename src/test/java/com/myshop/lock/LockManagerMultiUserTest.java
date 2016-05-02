package com.myshop.lock;

import com.myshop.SpringIntTestConfig;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
public class LockManagerMultiUserTest {
    @Autowired
    private LockManager lockManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager txMgr;

    private TransactionTemplate tt;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate.update("truncate table locks");
        tt = new TransactionTemplate(txMgr);
    }

    @Test
    public void multiUser() throws Exception {
        TransactionTemplate tt = new TransactionTemplate(txMgr);
        AtomicInteger lockFailCount = new AtomicInteger(0);
        AtomicInteger lockSuccessCount = new AtomicInteger(0);

        Runnable usecase = () -> {
            LockId lockId = null;
            try {
                lockId = tt.execute(status -> runFuncWithTryLock());
            } catch (LockException e) {
                lockFailCount.incrementAndGet();
                return;
            }
            lockSuccessCount.incrementAndGet();

            SleepUtil.sleep(2000);

            final LockId finalLockId = lockId;
            tt.execute(status -> runFuncWithReleaseLock(finalLockId));
        };

        int numberOfUsers = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfUsers);
        for (int i = 1; i <= numberOfUsers; i++) {
            executorService.submit(usecase);
        }
        executorService.shutdown();
        executorService.awaitTermination(3000, TimeUnit.SECONDS);

        assertThat(lockFailCount.get(), equalTo(numberOfUsers - 1));
        assertThat(lockSuccessCount.get(), equalTo(1));
    }

    @Autowired
    private OrderRepository orderRepository;

    private LockId runFuncWithTryLock() {
        LockId lockId = lockManager.tryLock("temptype", "001");
        orderRepository.findById(new OrderNo("ORDER-001"));
        SleepUtil.sleep(100);
        return lockId;
    }

    private Object runFuncWithReleaseLock(LockId lockId) {
        lockManager.releaseLock(lockId);
        return null;
    }
}
