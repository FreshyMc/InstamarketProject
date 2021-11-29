package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Subscriber;
import com.example.instamarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Integer countSubscribersBySeller(User seller);
}
