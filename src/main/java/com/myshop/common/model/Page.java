package com.myshop.common.model;

import java.util.List;

public class Page<T> {
    private List<T> items;
    private int page;
    private int size;
    private long totalCount;
    private int totalPages;

    public Page(List<T> items, int page, int size, long totalCount) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;
        calculateTotalPages();
    }

    private void calculateTotalPages() {
        totalPages = (int)(totalCount / size);
        if (totalCount % size > 0) {
            totalPages++;
        }
    }

    public List<T> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
