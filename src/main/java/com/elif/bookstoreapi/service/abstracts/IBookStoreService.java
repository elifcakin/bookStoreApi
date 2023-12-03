package com.elif.bookstoreapi.service.abstracts;

import com.elif.bookstoreapi.entity.dtos.BookStoreDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IBookStoreService {
    List<BookStoreDTO> getAll();

    BookStoreDTO getById(Integer id);

    BookStoreDTO add(BookStoreDTO bookStoreDTO);

    @Transactional
    BookStoreDTO update(BookStoreDTO bookStoreDTO);

    void delete(Integer id);
}
