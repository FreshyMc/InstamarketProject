package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offer_properties")
public class OfferProperty extends BaseEntity{
    private Offer offer;
    private String propertyName;
    private String propertyValue;

    public OfferProperty() {
    }

    @Column(nullable = false, length = 200)
    public String getPropertyName() {
        return propertyName;
    }

    public OfferProperty setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    @Column(nullable = false, length = 200)
    public String getPropertyValue() {
        return propertyValue;
    }

    public OfferProperty setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
        return this;
    }

    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public OfferProperty setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
