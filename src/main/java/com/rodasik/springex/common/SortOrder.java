package com.rodasik.springex.common;

public enum SortOrder {
    ASCENDING("asc"),
    DESCENDING("desc");

    private final String order;
    SortOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return order;
    }
}

