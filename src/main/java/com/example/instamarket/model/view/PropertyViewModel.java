package com.example.instamarket.model.view;

public class PropertyViewModel {
    private Long id;
    private String propertyName;
    private String propertyValue;

    public PropertyViewModel() {
    }

    public Long getId() {
        return id;
    }

    public PropertyViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public PropertyViewModel setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public PropertyViewModel setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
        return this;
    }
}
