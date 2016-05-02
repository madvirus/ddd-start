package com.myshop.lock;

import com.myshop.SpringIntTestConfig;
import com.myshop.common.model.Address;
import com.myshop.order.command.domain.*;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringIntTestConfig
public class OptimisticLockTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TransactionTemplate txTemplate;

    @Test
    public void optimisticLock() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<Result> changeResult = executorService.submit(this::changeShippingInfo);
        Future<Result> startResult = executorService.submit(this::startShipping);

        assertThat(changeResult.get().isSuccess(), equalTo(true));
        assertThat(startResult.get().isSuccess(), equalTo(false));
        assertThat(startResult.get().getException(), Matchers.instanceOf(OptimisticLockingFailureException.class));
        executorService.shutdown();
    }

    private Result changeShippingInfo() {
        try {
            txTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Order order = orderRepository.findById(new OrderNo("ORDER-001"));
                    sleep(1000);
                    order.changeShippingInfo(new ShippingInfo(new Address("zip", "addr1", "addr2"), "msg", new Receiver("name", "phone")));
                }
            });
            return Result.SUCCESS;
        } catch (Exception e) {
            return Result.fail(e);
        }
    }

    private Result startShipping() {
        try {
            txTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    Order order = orderRepository.findById(new OrderNo("ORDER-001"));
                    sleep(3000);
                    order.startShipping();
                }
            });
            return Result.SUCCESS;
        } catch (Exception e) {
            return Result.fail(e);
        }
    }

    private void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
    }

    public static class Result {
        public static Result SUCCESS = new Result(true, null);
        public static Result fail(Exception ex) {
            return new Result(false, ex);
        }

        private boolean success;

        private Exception exception;

        private Result(boolean success, Exception exception) {
            this.success = success;
            this.exception = exception;
        }

        public boolean isSuccess() {
            return success;
        }

        public Exception getException() {
            return exception;
        }
    }
}
