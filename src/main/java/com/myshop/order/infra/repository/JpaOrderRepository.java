package com.myshop.order.infra.repository;

import com.myshop.common.jpa.JpaQueryUtils;
import com.myshop.common.jpaspec.Specification;
import com.myshop.order.command.domain.Order;
import com.myshop.order.command.domain.OrderNo;
import com.myshop.order.command.domain.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findById(OrderNo id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public Order findByIdOptimisticLockMode(OrderNo id) {
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.lock.timeout", 2000);
        return entityManager.find(Order.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT, hints);
    }

    @Override
    public List<Order> findByOrdererId(String ordererId, int startRow, int fetchSize) {
        TypedQuery<Order> query = entityManager.createQuery(
                "select o from Order o " +
                        "where o.orderer.memberId.id = :ordererId " +
                        JpaQueryUtils.toJPQLOrderBy("o", "number.number desc"),
                Order.class);
        query.setParameter("ordererId", ordererId);
        query.setFirstResult(startRow);
        query.setMaxResults(fetchSize);
        return query.getResultList();
    }

    @Override
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Override
    public void remove(Order order) {
        entityManager.remove(order);
    }

    @Override
    public List<Order> findAll(Specification<Order> spec, String ... orders) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = cb.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        Predicate predicate = spec.toPredicate(root, cb);
        criteriaQuery.where(predicate);
        if (orders.length > 0) {
            criteriaQuery.orderBy(JpaQueryUtils.toJpaOrders(root, cb, orders));
        }
        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Long counts(Specification<Order> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(cb.count(root)).where(spec.toPredicate(root, cb));
        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Long countsAll() {
        TypedQuery<Long> query = entityManager.createQuery("select count(o) from Order o", Long.class);
        return query.getSingleResult();
    }

    @Override
    public OrderNo nextOrderNo() {
        int randomNo = ThreadLocalRandom.current().nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
        return new OrderNo(number);
    }

}
