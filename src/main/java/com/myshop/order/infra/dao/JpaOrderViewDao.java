package com.myshop.order.infra.dao;

import org.springframework.stereotype.Repository;
import com.myshop.order.query.dao.OrderViewDao;
import com.myshop.order.query.dto.OrderView;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class JpaOrderViewDao implements OrderViewDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<OrderView> selectByOrderer(String ordererId) {
        String selectQuery =
                "select new com.myshop.order.query.dto.OrderView(o, m, p) "+
                "from Order o join o.orderLines ol, Member m, Product p " +
                "where o.orderer.memberId.id = :ordererId "+
                "and o.orderer.memberId = m.id "+
                "and index(ol) = 0 " +
                "and ol.productId = p.id "+
                "order by o.number.number desc";
        TypedQuery<OrderView> query =
                em.createQuery(selectQuery, OrderView.class);
        query.setParameter("ordererId", ordererId);
        return query.getResultList();
    }
}
