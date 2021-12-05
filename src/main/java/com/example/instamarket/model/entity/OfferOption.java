package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offer_options")
public class OfferOption extends BaseEntity{
    @ManyToOne
    private Offer offer;
    @Column(nullable = false, length = 200)
    private String optionName;
    @Column(nullable = false, length = 200)
    private String optionValue;
    private boolean removed = false;

    public OfferOption() {
    }

    public String getOptionName() {
        return optionName;
    }

    public OfferOption setOptionName(String optionName) {
        this.optionName = optionName;
        return this;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public OfferOption setOptionValue(String optionValue) {
        this.optionValue = optionValue;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferOption setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public boolean isRemoved() {
        return removed;
    }

    public OfferOption setRemoved(boolean removed) {
        this.removed = removed;
        return this;
    }
}
