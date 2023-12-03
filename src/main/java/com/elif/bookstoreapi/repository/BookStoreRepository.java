package com.elif.bookstoreapi.repository;

import com.elif.bookstoreapi.entity.BookStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookStoreRepository extends JpaRepository<BookStore,Integer> {
}
