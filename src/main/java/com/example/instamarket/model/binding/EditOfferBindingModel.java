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

public class EditOfferBindingModel {
    @NotNull
    private Long id;
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

    public EditOfferBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public EditOfferBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EditOfferBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditOfferBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<MultipartFile> getOfferImages() {
        return offerImages;
    }

    public EditOfferBindingModel setOfferImages(Set<MultipartFile> offerImages) {
        this.offerImages = offerImages;
        return this;
    }

    public List<OptionBindingModel> getOptions() {
        return options;
    }

    public EditOfferBindingModel setOptions(List<OptionBindingModel> options) {
        this.options = options;
        return this;
    }

    public List<PropertyBindingModel> getProperties() {
        return properties;
    }

    public EditOfferBindingModel setProperties(List<PropertyBindingModel> properties) {
        this.properties = properties;
        return this;
    }

    public CategoriesEnum getOfferCategory() {
        return offerCategory;
    }

    public EditOfferBindingModel setOfferCategory(CategoriesEnum offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public Long getId() {
        return id;
    }

    public EditOfferBindingModel setId(Long id) {
        this.id = id;
        return this;
    }
}
