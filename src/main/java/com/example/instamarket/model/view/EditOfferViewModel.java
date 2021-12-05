package com.example.instamarket.model.view;

import com.example.instamarket.model.enums.CategoriesEnum;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EditOfferViewModel {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Set<OfferImageViewModel> offerImages = new HashSet<>();
    private List<OptionViewModel> options = new LinkedList<>();
    private List<PropertyViewModel> properties = new LinkedList<>();
    private CategoriesEnum offerCategory;

    public EditOfferViewModel() {
    }

    public String getTitle() {
        return title;
    }

    public EditOfferViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EditOfferViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditOfferViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<OfferImageViewModel> getOfferImages() {
        return offerImages;
    }

    public EditOfferViewModel setOfferImages(Set<OfferImageViewModel> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public List<OptionViewModel> getOptions() {
        return options;
    }

    public EditOfferViewModel setOptions(List<OptionViewModel> options) {
        this.options = options;
        return this;
    }

    public List<PropertyViewModel> getProperties() {
        return properties;
    }

    public EditOfferViewModel setProperties(List<PropertyViewModel> properties) {
        this.properties = properties;
        return this;
    }

    public CategoriesEnum getOfferCategory() {
        return offerCategory;
    }

    public EditOfferViewModel setOfferCategory(CategoriesEnum offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public Long getId() {
        return id;
    }

    public EditOfferViewModel setId(Long id) {
        this.id = id;
        return this;
    }
}
