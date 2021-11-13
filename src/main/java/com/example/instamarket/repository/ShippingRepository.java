package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Shipping;
import com.example.instamarket.model.enums.ShippingTypesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    Optional<Shipping> findShippingByShipping(ShippingTypesEnum shipping);
}
