package com.example.instamarket.repository;

import com.example.instamarket.model.entity.SellerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRequestRepository extends JpaRepository<SellerRequest, Long> {
    Optional<SellerRequest> findSellerRequestBySeller_Username(String username);

    List<SellerRequest> findAllByApprovedIsFalse();

    List<SellerRequest> findAllByApprovedIsTrue();
}
