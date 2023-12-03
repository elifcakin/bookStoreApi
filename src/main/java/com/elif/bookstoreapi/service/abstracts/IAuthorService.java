package com.elif.bookstoreapi.service.abstracts;

import com.elif.bookstoreapi.entity.dtos.AuthorDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IAuthorService {
    List<AuthorDTO> getAll();

    AuthorDTO getById(Integer id);

    @Transactional
    AuthorDTO add(AuthorDTO authorDTO);

    @Transactional
    AuthorDTO update(AuthorDTO authorDTO);

    @Transactional
    void delete(Integer id);
}
