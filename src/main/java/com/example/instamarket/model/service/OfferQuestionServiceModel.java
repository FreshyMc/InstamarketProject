package com.example.instamarket.model.service;

public class OfferQuestionServiceModel {
    private Long offerId;
    private String question;

    public OfferQuestionServiceModel() {
    }

    public Long getOfferId() {
        return offerId;
    }

    public OfferQuestionServiceModel setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public OfferQuestionServiceModel setQuestion(String question) {
        this.question = question;
        return this;
    }
}
