package com.example.instamarket.model.enums;

public enum ShippingTypesEnum {
    INTERNATIONAL("International Shipping"),
    COUNTRY_ONLY("In Country Shipping"),
    FREE("Free Shipping");

    private String displayName;

    ShippingTypesEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ShippingTypesEnum setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
