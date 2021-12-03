package com.example.instamarket.service;

import com.example.instamarket.model.view.SellerApprovalRequest;

import java.util.List;

public interface AdminService {
    void approveSeller(Long sellerRequestId);

    void disapproveSeller(Long sellerRequestId);

    List<SellerApprovalRequest> sellerApprovalRequests();

    List<SellerApprovalRequest> approvedSellers();
}
