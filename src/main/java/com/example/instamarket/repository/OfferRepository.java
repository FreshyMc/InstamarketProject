package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Category;
import com.example.instamarket.model.entity.Offer;
import com.example.instamarket.model.entity.Shipping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Page<Offer> findAllByTitleContainingAndDeleted(String title, boolean deleted, Pageable pageable);

    Page<Offer> findAllByTitleContainingAndOfferCategoryAndDeleted(String title, Category offerCategory, boolean deleted, Pageable pageable);

    //Basic filtering with category search
    @Query(value = "SELECT o FROM Offer o WHERE o.title LIKE %:title% AND o.shippingType = :shipping AND o.offerCategory = :category AND o.deleted = false")
    Page<Offer> findAllByTitleAndFreeShipping(@Param("title") String title, @Param("shipping") Shipping shipping, @Param("category") Category category,Pageable pageable);

    @Query(value = "SELECT o FROM Offer o JOIN WishList w ON o.id = w.offer.id WHERE o.title LIKE %:title% AND o.offerCategory = :category AND w.user.username = :username AND o.deleted = false AND w.removed = false")
    Page<Offer> findAllByTitleAndFavouriteUserOffers(@Param("title") String title, @Param("username") String username, @Param("category") Category category, Pageable pageable);

    @Query(value = "SELECT o FROM Offer o JOIN WishList w ON o.id = w.offer.id WHERE o.title LIKE %:title% AND o.shippingType = :shipping AND o.offerCategory = :category AND w.user.username = :username AND o.deleted = false AND w.removed = false")
    Page<Offer> findAllByTitleAndFreeShippingAndFavouriteUserOffers(@Param("title") String title, @Param("username") String username, @Param("shipping") Shipping shipping, @Param("category") Category category, Pageable pageable);

    //Basic filtering without categories search
    @Query(value = "SELECT o FROM Offer o WHERE o.title LIKE %:title% AND o.shippingType = :shipping AND o.deleted = false")
    Page<Offer> findAllByTitleAndFreeShipping(@Param("title") String title, @Param("shipping") Shipping shipping, Pageable pageable);

    @Query(value = "SELECT o FROM Offer o JOIN WishList w ON o.id = w.offer.id WHERE o.title LIKE %:title% AND w.user.username = :username AND o.deleted = false AND w.removed = false")
    Page<Offer> findAllByTitleAndFavouriteUserOffers(@Param("title") String title, @Param("username") String username, Pageable pageable);

    @Query(value = "SELECT o FROM Offer o JOIN WishList w ON o.id = w.offer.id WHERE o.title LIKE %:title% AND o.shippingType = :shipping AND w.user.username = :username AND o.deleted = false AND w.removed = false")
    Page<Offer> findAllByTitleAndFreeShippingAndFavouriteUserOffers(@Param("title") String title, @Param("username") String username, @Param("shipping") Shipping shipping, Pageable pageable);
}
