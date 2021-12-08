package com.example.instamarket.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_feedback")
public class OrderFeedback extends BaseEntity{
    @ManyToOne
    private Order order;
    @ManyToOne
    private User buyer;
    @Column(columnDefinition = "LONGTEXT")
    private String feedback;
    @Column(nullable = false)
    private Integer rating;

    public OrderFeedback() {
    }

    public Order getOrder() {
        return order;
    }

    public OrderFeedback setOrder(Order order) {
        this.order = order;
        return this;
    }

    public User getBuyer() {
        return buyer;
    }

    public OrderFeedback setBuyer(User buyer) {
        this.buyer = buyer;
        return this;
    }

    public String getFeedback() {
        return feedback;
    }

    public OrderFeedback setFeedback(String feedback) {
        this.feedback = feedback;
        return this;
    }

    public Integer getRating() {
        return rating;
    }

    public OrderFeedback setRating(Integer rating) {
        this.rating = rating;
        return this;
    }
}
