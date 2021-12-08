package com.example.instamarket.model.service;

public class FeedbackServiceModel {
    private Long orderId;
    private String feedback;
    private Integer rating;

    public FeedbackServiceModel() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public FeedbackServiceModel setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getFeedback() {
        return feedback;
    }

    public FeedbackServiceModel setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public FeedbackServiceModel setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
