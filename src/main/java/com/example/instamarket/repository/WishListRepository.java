package com.example.instamarket.repository;

import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByOfferIdAndUser(Long id, User user);
}
