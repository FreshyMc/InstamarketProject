package com.example.instamarket.repository;

import com.example.instamarket.model.entity.User;
import com.example.instamarket.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByOfferIdAndUser(Long id, User user);

    @Query(value = "SELECT w FROM WishList w WHERE w.removed = false AND w.user.id = :id AND w.offer.deleted = false ORDER BY w.addedAt DESC")
    List<WishList> findAllUserWishListItems(@Param("id") Long id);

    @Query(value = "DELETE FROM WishList w WHERE w.removed = true")
    void clearRemovedItems();
}
