package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offer_properties")
public class OfferProperty extends BaseEntity{
    @ManyToOne
    private Offer offer;
    @Column(nullable = false, length = 200)
    private String propertyName;
    @Column(nullable = false, length = 200)
    private String propertyValue;
    private boolean removed = false;

    public OfferProperty() {
    }

    public String getPropertyName() {
        return propertyName;
    }

    public OfferProperty setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public OfferProperty setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferProperty setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public boolean isRemoved() {
        return removed;
    }

    public OfferProperty setRemoved(boolean removed) {
        this.removed = removed;
        return this;
    }
}
