package com.example.instamarket.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "offer_images")
public class OfferImage extends BaseEntity {
    private Offer offer;
    private String imageUrl;

    public OfferImage() {
    }

    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public OfferImage setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public OfferImage setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
