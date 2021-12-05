package com.example.instamarket.repository;

import com.example.instamarket.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {
    Integer countOffersBySellerAndDeleted(User seller, boolean deleted);

    Slice<Offer> findAllBySeller_Id(Long sellerId, Pageable pageable);

    Optional<Offer> findOfferByIdAndSeller(Long id, User seller);
}
