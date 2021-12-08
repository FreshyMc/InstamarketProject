package com.example.instamarket.model.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class FeedbackBindingModel {
    @NotNull
    private Long orderId;
    private String feedback;
    @PositiveOrZero
    private Integer rating;

    public FeedbackBindingModel() {
    }

    public String getFeedback() {
        return feedback;
    }

    public FeedbackBindingModel setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public FeedbackBindingModel setRating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public Long getOrderId() {
        return orderId;
    }

    public FeedbackBindingModel setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }
}
