package com.example.instamarket.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OfferQuestionBindingModel {
    @NotNull
    private Long offerId;
    @NotBlank
    private String question;

    public OfferQuestionBindingModel() {
    }

    public Long getOfferId() {
        return offerId;
    }

    public OfferQuestionBindingModel setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public String getQuestion() {
        return question;
    }

    public OfferQuestionBindingModel setQuestion(String question) {
        this.question = question;
        return this;
    }
}
