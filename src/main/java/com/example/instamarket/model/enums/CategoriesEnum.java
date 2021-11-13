package com.example.instamarket.model.enums;

public enum CategoriesEnum {
    VEHICLE("Vehicles"),
    HOME_APPLIANCE("Home appliances"),
    REAL_ESTATES("Real estates"),
    ELECTRONICS("Electronics"),
    LARGE_APPLIANCE("Large appliances"),
    BOOKS("Books");

    private String displayName;

    CategoriesEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public CategoriesEnum setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
