package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.CategoriesEnum;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private CategoriesEnum category;
    @Column(nullable = false)
    private String displayName;

    public Category() {
    }

    public CategoriesEnum getCategory() {
        return category;
    }

    public Category setCategory(CategoriesEnum category) {
        this.category = category;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Category setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
}
