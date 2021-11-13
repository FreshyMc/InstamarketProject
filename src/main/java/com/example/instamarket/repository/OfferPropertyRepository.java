package com.example.instamarket.repository;

import com.example.instamarket.model.entity.OfferProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferPropertyRepository extends JpaRepository<OfferProperty, Long> {
}
