package com.example.instamarket.model.enums;

public enum OrderStatusEnum {
    WAITING("Order Waiting"),
    ACCEPTED("Order Accepted"),
    CANCELED("Order Cancelled"),
    SHIPPED("Order Shipped"),
    COMPLETED("Order Completed");

    private String displayName;

    OrderStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public OrderStatusEnum setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
