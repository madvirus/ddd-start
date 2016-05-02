package com.myshop.order.infra.paygate;

import com.myshop.order.command.application.RefundService;
import org.springframework.stereotype.Component;

@Component
public class ExternalRefundService implements RefundService {
    @Override
    public void refund(String orderNumber) {
        System.out.printf("refund order[%s]\n", orderNumber);
    }
}
