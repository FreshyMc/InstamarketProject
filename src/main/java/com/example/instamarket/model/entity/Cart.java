package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
public class Cart extends BaseEntity{
    @ManyToOne
    private User buyer;
    @ManyToOne
    private Offer offer;
    @ManyToOne
    private OfferOption offerOption;
    private LocalDateTime addedAt;
    @Column
    private boolean removed = false;

    public Cart() {
    }

    public User getBuyer() {
        return buyer;
    }

    public Cart setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public Cart setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public OfferOption getOfferOption() {
        return offerOption;
    }

    public Cart setOfferOption(OfferOption offerOption) {
        this.offerOption = offerOption;
        return this;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public Cart setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
        return this;
    }

    @PrePersist
    public void populateAddedAt(){
        this.setAddedAt(LocalDateTime.now());
    }

    public boolean isRemoved() {
        return removed;
    }

    public Cart setRemoved(boolean removed) {
        this.removed = removed;
        return this;
    }
}
