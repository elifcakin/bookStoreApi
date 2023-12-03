package com.elif.bookstoreapi.service.abstracts;

import com.elif.bookstoreapi.entity.dtos.BookDTO;

import java.util.List;

public interface IBookService {
    List<BookDTO> getAll();

    BookDTO getById(Integer id);

    BookDTO add(BookDTO bookDTO);

    BookDTO update(BookDTO bookDTO);

    void delete(Integer id);
}
