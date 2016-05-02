package com.myshop.common.order;

public abstract class Orders {
    public static SortOrder ascending(String path) {
        return new Ascending(path);
    }

    public static SortOrder descending(String path) {
        return new Descending(path);
    }
}
