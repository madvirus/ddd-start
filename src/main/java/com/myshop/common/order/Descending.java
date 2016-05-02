package com.myshop.common.order;

public class Descending extends AbstractOrder {
    public Descending(String path) {
        super(path);
    }

    @Override
    public boolean isAscending() {
        return false;
    }
}
