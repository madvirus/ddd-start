package com.myshop.common.jpaspec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaBuilder cb);
}
