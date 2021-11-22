package com.example.instamarket.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
public class Cart extends BaseEntity{
    private User buyer;
    private Offer offer;
    private LocalDateTime addedAt;

    public Cart() {
    }

    @ManyToOne
    public User getBuyer() {
        return buyer;
    }

    public Cart setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public Cart setOffer(Offer offer) {
        this.offer = offer;
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
}
