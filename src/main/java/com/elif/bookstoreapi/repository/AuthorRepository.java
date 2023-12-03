package com.elif.bookstoreapi.repository;

import com.elif.bookstoreapi.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
}
