package com.example.instamarket.model.view;

import java.time.LocalDateTime;

public class SellerApprovalRequest {
    private Long id;
    private String username;
    private Long userId;
    private LocalDateTime requestTime;

    public SellerApprovalRequest() {
    }

    public Long getId() {
        return id;
    }

    public SellerApprovalRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SellerApprovalRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public SellerApprovalRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public SellerApprovalRequest setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
        return this;
    }
}
