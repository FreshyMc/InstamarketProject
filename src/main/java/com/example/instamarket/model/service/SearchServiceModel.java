package com.example.instamarket.model.service;

import com.example.instamarket.model.enums.CategoriesEnum;

public class SearchServiceModel {
    private String search;
    private CategoriesEnum category;

    public SearchServiceModel() {
    }

    public String getSearch() {
        return search;
    }

    public SearchServiceModel setSearch(String search) {
        this.search = search;
        return this;
    }

    public CategoriesEnum getCategory() {
        return category;
    }

    public SearchServiceModel setCategory(CategoriesEnum category) {
        this.category = category;
        return this;
    }
}
