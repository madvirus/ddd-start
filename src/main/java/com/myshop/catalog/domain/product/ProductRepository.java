package com.myshop.catalog.domain.product;

import com.myshop.catalog.domain.category.CategoryId;

import java.util.List;

public interface ProductRepository {
    void save(Product product);

    Product findById(ProductId id);

    void remove(Product product);

    List<Product> findAll();

    List<Product> findByCategoryId(CategoryId categoryId, int page, int size);

    long countsByCategoryId(CategoryId categoryId);
}
