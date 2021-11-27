package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "offer_questions")
public class OfferQuestion extends BaseEntity{
    @ManyToOne
    private User inquiring;
    @ManyToOne
    private Offer offer;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String question;

    public OfferQuestion() {
    }

    public User getInquiring() {
        return inquiring;
    }

    public OfferQuestion setInquiring(User inquiring) {
        this.inquiring = inquiring;
        return this;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferQuestion setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public OfferQuestion setQuestion(String question) {
        this.question = question;
        return this;
    }
}
