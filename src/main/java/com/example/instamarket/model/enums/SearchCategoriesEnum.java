package com.example.instamarket.model.enums;

public enum SearchCategoriesEnum {
    ALL("All categories"),
    VEHICLE("Vehicles"),
    HOME_APPLIANCE("Home appliances"),
    ELECTRONICS("Electronics"),
    LARGE_APPLIANCE("Large appliances"),
    BOOKS("Books");

    private String displayName;

    SearchCategoriesEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public SearchCategoriesEnum setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
