package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "offer_options")
public class OfferOption extends BaseEntity{
    private Offer offer;
    private String optionName;
    private String optionValue;

    public OfferOption() {
    }

    @Column(nullable = false, length = 200)
    public String getOptionName() {
        return optionName;
    }

    public OfferOption setOptionName(String optionName) {
        this.optionName = optionName;
        return this;
    }

    @Column(nullable = false, length = 200)
    public String getOptionValue() {
        return optionValue;
    }

    public OfferOption setOptionValue(String optionValue) {
        this.optionValue = optionValue;
        return this;
    }

    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public OfferOption setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }
}
