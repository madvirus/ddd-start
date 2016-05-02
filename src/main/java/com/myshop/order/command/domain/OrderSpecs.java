package com.myshop.order.command.domain;

import com.myshop.common.jpaspec.Specification;
import com.myshop.member.domain.MemberId_;

import java.util.Date;

public class OrderSpecs {
    public static Specification<Order> orderer(String ordererId) {
        return (root, cb) -> cb.equal(
                root.get(Order_.orderer).get(Orderer_.memberId).get(MemberId_.id),
                ordererId);
    }

    public static Specification<Order> between(Date from, Date to) {
        return (root, cb) -> cb.between(root.get(Order_.orderDate), from, to);
    }
}
