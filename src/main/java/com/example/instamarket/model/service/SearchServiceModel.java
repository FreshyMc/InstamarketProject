package com.example.instamarket.model.service;

import com.example.instamarket.model.enums.CategoriesEnum;

public class SearchServiceModel {
    private String search;
    private CategoriesEnum category;
    private Boolean freeShipping;
    private Boolean favouriteOffer;

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

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public SearchServiceModel setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
        return this;
    }

    public Boolean getFavouriteOffer() {
        return favouriteOffer;
    }

    public SearchServiceModel setFavouriteOffer(Boolean favouriteOffer) {
        this.favouriteOffer = favouriteOffer;
        return this;
    }
}
