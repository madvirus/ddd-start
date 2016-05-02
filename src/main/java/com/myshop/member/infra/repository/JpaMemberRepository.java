package com.myshop.member.infra.repository;

import com.myshop.common.jpa.JpaQueryUtils;
import com.myshop.common.jpaspec.Specification;
import com.myshop.common.order.SortOrder;
import com.myshop.member.domain.Member;
import com.myshop.member.domain.MemberId;
import com.myshop.member.domain.MemberRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaMemberRepository implements MemberRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Member> findAll(Specification<Member> spec, List<SortOrder> orders, int startRow, int maxResults) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Member> criteriaQuery = cb.createQuery(Member.class);
        Root<Member> root = criteriaQuery.from(Member.class);
        Predicate predicate = spec.toPredicate(root, cb);
        criteriaQuery.where(predicate);
        if (!orders.isEmpty()) {
            criteriaQuery.orderBy(JpaQueryUtils.toJpaOrders(root, cb, orders));
        }
        TypedQuery<Member> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(startRow);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public Member findById(MemberId memberId) {
        return entityManager.find(Member.class, memberId);
    }
}
