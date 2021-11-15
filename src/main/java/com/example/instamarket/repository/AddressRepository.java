package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Address a WHERE a.id = :id")
    void deleteAddressById(@Param("id") Long id);
}
