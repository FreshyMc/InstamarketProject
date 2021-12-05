package com.example.instamarket.model.service;

import com.example.instamarket.model.binding.OptionBindingModel;
import com.example.instamarket.model.binding.PropertyBindingModel;
import com.example.instamarket.model.enums.CategoriesEnum;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class EditOfferServiceModel {
    private Long id;
    private String title;
    private BigDecimal price;
    private String description;
    private Set<MultipartFile> offerImages = new HashSet<>();
    private List<OptionBindingModel> options = new LinkedList<>();
    private List<PropertyBindingModel> properties = new LinkedList<>();
    private CategoriesEnum offerCategory;

    public EditOfferServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public EditOfferServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EditOfferServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditOfferServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<MultipartFile> getOfferImages() {
        return offerImages;
    }

    public EditOfferServiceModel setOfferImages(Set<MultipartFile> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public List<OptionBindingModel> getOptions() {
        return options;
    }

    public EditOfferServiceModel setOptions(List<OptionBindingModel> options) {
        this.options = options;
        return this;
    }

    public List<PropertyBindingModel> getProperties() {
        return properties;
    }

    public EditOfferServiceModel setProperties(List<PropertyBindingModel> properties) {
        this.properties = properties;
        return this;
    }

    public CategoriesEnum getOfferCategory() {
        return offerCategory;
    }

    public EditOfferServiceModel setOfferCategory(CategoriesEnum offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public Long getId() {
        return id;
    }

    public EditOfferServiceModel setId(Long id) {
        this.id = id;
        return this;
    }
}
