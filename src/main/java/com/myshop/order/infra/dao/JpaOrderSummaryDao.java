package com.myshop.order.infra.dao;

import com.myshop.common.jpaspec.Specification;
import com.myshop.order.query.dao.OrderSummaryDao;
import com.myshop.order.query.dto.OrderSummary;
import com.myshop.order.query.dto.OrderSummary_;
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
public class JpaOrderSummaryDao implements OrderSummaryDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<OrderSummary> selectByOrderer(String ordererId) {
        TypedQuery<OrderSummary> query = em.createQuery("select os from OrderSummary " +
                "os where os.ordererId = :ordererId " +
                "order by os.orderDate desc", OrderSummary.class);
        query.setParameter("ordererId", ordererId);
        return query.getResultList();
    }

    @Override
    public List<OrderSummary> select(Specification<OrderSummary> spec, int firstRow, int maxResults) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<OrderSummary> criteriaQuery = cb.createQuery(OrderSummary.class);
        Root<OrderSummary> root = criteriaQuery.from(OrderSummary.class);
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, cb);
            criteriaQuery.where(predicate);
        }
        criteriaQuery.orderBy(cb.desc(root.get(OrderSummary_.number)));
        TypedQuery<OrderSummary> query = em.createQuery(criteriaQuery);
        query.setFirstResult(firstRow);
        query.setMaxResults(maxResults);
        return query.getResultList();
    }

    @Override
    public long counts(Specification<OrderSummary> spec) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<OrderSummary> root = criteriaQuery.from(OrderSummary.class);
        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, cb);
            criteriaQuery.where(predicate);
        }
        criteriaQuery.select(cb.count(root));
        TypedQuery<Long> query = em.createQuery(criteriaQuery);
        return query.getSingleResult().longValue();
    }
}
