package com.example.instamarket.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offer_questions")
public class OfferQuestion extends BaseEntity{
    @ManyToOne
    private User inquiring;
    @ManyToOne
    private Offer offer;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String question;
    @OneToOne
    private OfferAnswer answer;
    private LocalDateTime addedAt;

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

    public OfferAnswer getAnswer() {
        return answer;
    }

    public OfferQuestion setAnswer(OfferAnswer answer) {
        this.answer = answer;
        return this;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public OfferQuestion setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
        return this;
    }

    @PrePersist
    public void populateAddedAt(){
        this.setAddedAt(LocalDateTime.now());
    }
}
