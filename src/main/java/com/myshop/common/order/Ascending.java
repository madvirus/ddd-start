package com.myshop.common.order;

public class Ascending extends AbstractOrder {
    public Ascending(String path) {
        super(path);
    }

    @Override
    public boolean isAscending() {
        return true;
    }
}
