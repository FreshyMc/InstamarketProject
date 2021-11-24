package com.example.instamarket.model.service;

import com.example.instamarket.model.enums.CategoriesEnum;

import java.math.BigDecimal;

public class SearchServiceModel {
    private String search;
    private CategoriesEnum category;
    private Boolean freeShipping;
    private Boolean favouriteOffer;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

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

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public SearchServiceModel setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public SearchServiceModel setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }
}
