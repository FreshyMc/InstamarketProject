package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
public class WishList extends BaseEntity{
    @ManyToOne
    private User user;
    @ManyToOne
    private Offer offer;
    private LocalDateTime addedAt;
    @Column
    private boolean removed = false;

    public WishList() {
    }

    public User getUser() {
        return user;
    }

    public WishList setUser(User user) {
        this.user = user;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public WishList setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public WishList setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
        return this;
    }

    @PrePersist
    public void populateAddedAt(){
        this.setAddedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void updateAddedAt(){
        this.setAddedAt(LocalDateTime.now());
    }

    public boolean isRemoved() {
        return removed;
    }

    public WishList setRemoved(boolean removed) {
        this.removed = removed;
        return this;
    }
}
