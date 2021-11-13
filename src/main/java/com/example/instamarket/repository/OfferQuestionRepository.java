package com.example.instamarket.repository;

import com.example.instamarket.model.entity.OfferQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferQuestionRepository extends JpaRepository<OfferQuestion, Long> {
}
