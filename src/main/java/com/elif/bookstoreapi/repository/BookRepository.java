package com.elif.bookstoreapi.repository;

import com.elif.bookstoreapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {
}
