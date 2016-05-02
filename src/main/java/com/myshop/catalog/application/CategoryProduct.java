package com.myshop.catalog.application;

import com.myshop.catalog.domain.category.Category;
import com.myshop.common.model.Page;

import java.util.List;

public class CategoryProduct extends Page<ProductSummary> {
    private Category category;
    public CategoryProduct(Category category, List<ProductSummary> items, int page, int size, long totalCount) {
        super(items, page, size, totalCount);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }
}
