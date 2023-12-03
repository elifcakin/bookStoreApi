package com.elif.bookstoreapi.repository;

import com.elif.bookstoreapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
