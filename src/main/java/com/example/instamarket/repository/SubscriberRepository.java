package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Subscriber;
import com.example.instamarket.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    @Query("SELECT COUNT(s) FROM Subscriber s WHERE s.seller = :seller AND s.isSubscribed = true")
    Integer countSubscribers(User seller);

    Optional<Subscriber> findBySubscriberAndSeller(User subscriber, User seller);
}
