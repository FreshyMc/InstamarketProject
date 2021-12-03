package com.example.instamarket.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "seller_requests")
public class SellerRequest extends BaseEntity{
    @ManyToOne
    private User seller;
    private boolean approved = false;
    private LocalDateTime requestTime;

    public SellerRequest() {
    }

    public User getSeller() {
        return seller;
    }

    public SellerRequest setSeller(User seller) {
        this.seller = seller;
        return this;
    }

    public boolean isApproved() {
        return approved;
    }

    public SellerRequest setApproved(boolean approved) {
        this.approved = approved;
        return this;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public SellerRequest setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    @PrePersist
    public void populateRequestTime(){
        this.setRequestTime(LocalDateTime.now());
    }
}
