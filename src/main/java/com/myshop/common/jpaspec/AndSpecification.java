package com.myshop.common.jpaspec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class AndSpecification<T> implements Specification<T> {
    private List<Specification<T>> specs;

    public AndSpecification(Specification<T> ... specs) {
        this.specs = Arrays.asList(specs);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaBuilder cb) {
        Predicate[] predicates = specs.stream()
                .map(spec -> spec.toPredicate(root, cb))
                .toArray(size -> new Predicate[size]);
        return cb.and(predicates);
    }
}
