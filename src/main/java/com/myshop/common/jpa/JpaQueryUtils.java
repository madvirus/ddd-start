package com.myshop.common.jpa;

import com.myshop.common.order.SortOrder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class JpaQueryUtils {

    public static <T> List<Order> toJpaOrders(
            Root<T> root, CriteriaBuilder cb, String... orders) {
        if (orders == null || orders.length == 0) return Collections.emptyList();

        return Arrays.stream(orders)
                .map(orderStr -> toJpaOrder(root, cb, orderStr))
                .collect(toList());
    }

    private static <T> Order toJpaOrder(
            Root<T> root, CriteriaBuilder cb, String orderStr) {
        String[] orderClause = orderStr.split(" ");
        boolean ascending = true;
        if (orderClause.length == 2 && orderClause[1].equalsIgnoreCase("desc")) {
            ascending = false;
        }
        String[] paths = orderClause[0].split("\\.");
        Path<Object> path = root.get(paths[0]);
        for (int i = 1; i < paths.length; i++) {
            path = path.get(paths[i]);
        }
        return ascending ? cb.asc(path) : cb.desc(path);
    }

    public static String toJPQLOrderBy(String alias, String... orders) {
        if (orders == null || orders.length == 0) return "";
        String orderParts = Arrays.stream(orders)
                .map(order -> alias + "." + order)
                .collect(joining(", "));
        return "order by " + orderParts;
    }

    public static <T> List<Order> toJpaOrders(
            Root<T> root, CriteriaBuilder cb, List<SortOrder> orders) {
        if (orders == null || orders.isEmpty()) return Collections.emptyList();
        return orders.stream()
                .map(sortOrder -> toJpaOrder(root, cb, sortOrder))
                .collect(toList());
    }

    private static <T> Order toJpaOrder(Root<T> root, CriteriaBuilder cb, SortOrder sortOrder) {
        String[] paths = sortOrder.getPath().split("\\.");
        Path<Object> path = root.get(paths[0]);
        for (int i = 1; i < paths.length; i++) {
            path = path.get(paths[i]);
        }
        return sortOrder.isAscending() ? cb.asc(path) : cb.desc(path);
    }

}
