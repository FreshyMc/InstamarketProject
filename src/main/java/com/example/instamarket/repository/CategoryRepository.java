package com.example.instamarket.repository;

import com.example.instamarket.model.entity.Category;
import com.example.instamarket.model.enums.CategoriesEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByCategory(CategoriesEnum category);
}
