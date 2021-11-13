package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Role;
import com.example.instamarket.model.enums.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RolesEnum name);
}
