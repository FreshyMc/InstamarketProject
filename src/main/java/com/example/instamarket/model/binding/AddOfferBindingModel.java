package com.example.instamarket.model.binding;

import com.example.instamarket.model.enums.CategoriesEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AddOfferBindingModel {
    @NotBlank
    private String title;
    @NotNull
    @PositiveOrZero
    private BigDecimal price;
    @NotBlank
    private String description;
    @NotNull
    private Set<MultipartFile> offerImages = new HashSet<>();
    private List<OptionBindingModel> options = new LinkedList<>();
    private List<PropertyBindingModel> properties = new LinkedList<>();
    @NotNull
    private CategoriesEnum offerCategory;

    public AddOfferBindingModel() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddOfferBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public List<OptionBindingModel> getOptions() {
        return options;
    }

    public AddOfferBindingModel setOptions(List<OptionBindingModel> options) {
        this.options = options;
        return this;
    }

    public List<PropertyBindingModel> getProperties() {
        return properties;
    }

    public AddOfferBindingModel setProperties(List<PropertyBindingModel> properties) {
        this.properties = properties;
        return this;
    }

    public CategoriesEnum getOfferCategory() {
        return offerCategory;
    }

    public AddOfferBindingModel setOfferCategory(CategoriesEnum offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public Set<MultipartFile> getOfferImages() {
        return offerImages;
    }

    public AddOfferBindingModel setOfferImages(Set<MultipartFile> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AddOfferBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddOfferBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
