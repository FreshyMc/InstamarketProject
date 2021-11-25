package com.example.instamarket.model.dto;

import javax.validation.constraints.NotNull;

public class CartDTO {
    private Long offerId;
    private Boolean isAdded;

    public CartDTO() {
    }

    public Long getOfferId() {
        return offerId;
    }

    public CartDTO setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public Boolean getAdded() {
        return isAdded;
    }

    public CartDTO setAdded(Boolean added) {
        isAdded = added;
        return this;
    }
}
