package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Category;
import com.example.instamarket.model.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findAllByTitleContaining(String title, Pageable pageable);

    Page<Offer> findAllByTitleContainingAndOfferCategory(String title, Category offerCategory, Pageable pageable);
}
