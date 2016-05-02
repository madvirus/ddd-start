package com.myshop.common.jpaspec;

public class Specs {
    public static <T> Specification<T> and(Specification<T> ... specs) {
        return new AndSpecification<>(specs);
    }

    public static <T> Specification<T> or(Specification<T> ... specs) {
        return new OrSpecification<>(specs);
    }
}
