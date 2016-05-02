package com.myshop.catalog.domain.category;

import java.util.List;

public interface CategoryRepository {
    Category findById(CategoryId id);

    List<Category> findAll();
}
