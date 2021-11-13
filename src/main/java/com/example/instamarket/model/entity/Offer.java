package com.example.instamarket.model.entity;

import com.example.instamarket.model.enums.CategoriesEnum;
import com.example.instamarket.model.enums.ShippingTypesEnum;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{
    private User seller;
    private String title;
    private BigDecimal price;
    private String description;
    private Set<OfferImage> images;
    private Set<OfferOption> offerOptions;
    private Set<OfferProperty> offerProperties;
    private Category offerCategory;
    private Shipping shippingType;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Offer() {
    }

    @ManyToOne
    public User getSeller() {
        return seller;
    }

    public Offer setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Offer setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @ManyToOne
    public Category getOfferCategory() {
        return offerCategory;
    }

    public Offer setOfferCategory(Category offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    @ManyToOne
    public Shipping getShippingType() {
        return shippingType;
    }

    public Offer setShippingType(Shipping shippingType) {
        this.shippingType = shippingType;
        return this;
    }

    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<OfferOption> getOfferOptions() {
        return offerOptions;
    }

    public Offer setOfferOptions(Set<OfferOption> offerOptions) {
        this.offerOptions = offerOptions;
        return this;
    }

    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<OfferProperty> getOfferProperties() {
        return offerProperties;
    }

    public Offer setOfferProperties(Set<OfferProperty> offerProperties) {
        this.offerProperties = offerProperties;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Offer setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public Offer setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public Offer setTitle(String title) {
        this.title = title;
        return this;
    }

    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<OfferImage> getImages() {
        return images;
    }

    public Offer setImages(Set<OfferImage> images) {
        this.images = images;
        return this;
    }

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    public String getDescription() {
        return description;
    }

    public Offer setDescription(String description) {
        this.description = description;
        return this;
    }

    @PrePersist
    private void populateCreatedAt(){
        this.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    private void populateModifiedAt(){
        this.setModifiedAt(LocalDateTime.now());
    }
}
