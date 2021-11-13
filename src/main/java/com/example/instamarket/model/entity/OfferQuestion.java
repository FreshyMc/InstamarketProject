package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "offer_questions")
public class OfferQuestion extends BaseEntity{
    private User inquiring;
    private Offer offer;
    private String question;

    public OfferQuestion() {
    }

    @ManyToOne
    public User getInquiring() {
        return inquiring;
    }

    public OfferQuestion setInquiring(User inquiring) {
        this.inquiring = inquiring;
        return this;
    }

    @ManyToOne
    public Offer getOffer() {
        return offer;
    }

    public OfferQuestion setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    public String getQuestion() {
        return question;
    }

    public OfferQuestion setQuestion(String question) {
        this.question = question;
        return this;
    }
}
