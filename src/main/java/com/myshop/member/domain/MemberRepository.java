package com.myshop.member.domain;

import com.myshop.common.order.SortOrder;
import com.myshop.common.jpaspec.Specification;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll(Specification<Member> spec,
                         List<SortOrder> orders,
                         int startRow, int maxResults);

    Member findById(MemberId memberId);
}
