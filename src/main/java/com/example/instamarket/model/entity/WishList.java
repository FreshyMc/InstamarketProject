package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "wishlist")
public class WishList extends BaseEntity{
    private User user;
    private Offer offer;
    private LocalDateTime addedAt;

    public WishList() {
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public WishList setUser(User user) {
        this.user = user;
        return this;
    }

    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public WishList setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Column(nullable = false)
    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public WishList setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
        return this;
    }
}
