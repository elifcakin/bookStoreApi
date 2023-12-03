package com.elif.bookstoreapi.repository;

import com.elif.bookstoreapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
