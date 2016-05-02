package com.myshop.common.order;

public abstract class AbstractOrder implements SortOrder {
    private String path;

    public AbstractOrder(String path) {
        this.path = path;
    }

    @Override
    public String getPath() {
        return path;
    }
}
