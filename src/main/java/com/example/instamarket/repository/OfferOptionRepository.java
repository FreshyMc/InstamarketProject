package com.example.instamarket.repository;

import com.example.instamarket.model.entity.OfferOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferOptionRepository extends JpaRepository<OfferOption, Long> {
}
