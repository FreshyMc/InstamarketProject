package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{
    @ManyToOne
    private User seller;
    @Column(nullable = false)
    private String title;
    private BigDecimal price;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String description;
    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OfferImage> images;
    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OfferOption> offerOptions;
    @OneToMany(mappedBy = "offer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OfferProperty> offerProperties;
    @ManyToOne
    private Category offerCategory;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @Column
    private boolean deleted = false;

    public Offer() {
    }

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

    public Category getOfferCategory() {
        return offerCategory;
    }

    public Offer setOfferCategory(Category offerCategory) {
        this.offerCategory = offerCategory;
        return this;
    }

    public List<OfferOption> getOfferOptions() {
        return offerOptions;
    }

    public Offer setOfferOptions(List<OfferOption> offerOptions) {
        this.offerOptions = offerOptions;
        return this;
    }

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

    public String getTitle() {
        return title;
    }

    public Offer setTitle(String title) {
        this.title = title;
        return this;
    }

    public Set<OfferImage> getImages() {
        return images;
    }

    public Offer setImages(Set<OfferImage> images) {
        this.images = images;
        return this;
    }

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

    public boolean isDeleted() {
        return deleted;
    }

    public Offer setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
