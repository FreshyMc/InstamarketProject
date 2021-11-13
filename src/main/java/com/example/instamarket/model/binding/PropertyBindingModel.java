package com.example.instamarket.model.binding;

public class PropertyBindingModel {
    private String name;
    private String value;

    public PropertyBindingModel() {
    }

    public String getName() {
        return name;
    }

    public PropertyBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public PropertyBindingModel setValue(String value) {
        this.value = value;
        return this;
    }
}
