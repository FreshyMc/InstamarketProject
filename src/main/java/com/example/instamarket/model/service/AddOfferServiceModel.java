package com.example.instamarket.model.service;

import com.example.instamarket.model.binding.OptionBindingModel;
import com.example.instamarket.model.binding.PropertyBindingModel;
import com.example.instamarket.model.entity.*;
import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.ShippingTypesEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AddOfferServiceModel {
    private String title;
    private BigDecimal price;
    private String description;
    private Set<MultipartFile> offerImages;
    private List<OptionBindingModel> options = new LinkedList<>();
    private List<PropertyBindingModel> properties = new LinkedList<>();
    private CategoriesEnum offerCategory;
    private ShippingTypesEnum shippingType;

    public AddOfferServiceModel() {
    }

    public String getTitle() {
        return title;
    }

    public AddOfferServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddOfferServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddOfferServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<MultipartFile> getOfferImages() {
        return offerImages;
    }

    public AddOfferServiceModel setOfferImages(Set<MultipartFile> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public List<OptionBindingModel> getOptions() {
        return options;
    }

    public AddOfferServiceModel setOptions(List<OptionBindingModel> options) {
        this.options = options;
        return this;
    }

    public List<PropertyBindingModel> getProperties() {
        return properties;
    }

    public AddOfferServiceModel setProperties(List<PropertyBindingModel> properties) {
        this.properties = properties;
        return this;
    }

    public CategoriesEnum getOfferCategory() {
        return offerCategory;
    }

    public AddOfferServiceModel setOfferCategory(CategoriesEnum offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public ShippingTypesEnum getShippingType() {
        return shippingType;
    }

    public AddOfferServiceModel setShippingType(ShippingTypesEnum shippingType) {
        this.shippingType = shippingType;
        return this;
    }
}
