package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.CategoriesEnum;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    private CategoriesEnum category;
    private String displayName;

    public Category() {
    }

    @Enumerated(EnumType.STRING)
    public CategoriesEnum getCategory() {
        return category;
    }

    public Category setCategory(CategoriesEnum category) {
        this.category = category;
        return this;
    }

    @Column(nullable = false)
    public String getDisplayName() {
        return displayName;
    }

    public Category setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
