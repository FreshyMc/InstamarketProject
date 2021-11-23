package com.example.instamarket.model.binding;

import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.SearchCategoriesEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SearchOfferBindingModel {
    @NotBlank
    @Size(min = 3)
    private String search;
    @NotNull
    private SearchCategoriesEnum category;
    @NotNull
    private Boolean freeShipping;
    @NotNull
    private Boolean favouriteOffer;

    public SearchOfferBindingModel() {
    }

    public String getSearch() {
        return search;
    }

    public SearchOfferBindingModel setSearch(String search) {
        this.search = search;
        return this;
    }

    public SearchCategoriesEnum getCategory() {
        return category;
    }

    public SearchOfferBindingModel setCategory(SearchCategoriesEnum category) {
        this.category = category;
        return this;
    }

    public Boolean getFreeShipping() {
        return freeShipping;
    }

    public SearchOfferBindingModel setFreeShipping(Boolean freeShipping) {
        this.freeShipping = freeShipping;
        return this;
    }

    public Boolean getFavouriteOffer() {
        return favouriteOffer;
    }

    public SearchOfferBindingModel setFavouriteOffer(Boolean favouriteOffer) {
        this.favouriteOffer = favouriteOffer;
        return this;
    }
}
