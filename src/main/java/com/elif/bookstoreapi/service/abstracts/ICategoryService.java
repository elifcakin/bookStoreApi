package com.elif.bookstoreapi.service.abstracts;

import com.elif.bookstoreapi.entity.dtos.CategoryDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO getById(Integer id);

    CategoryDTO add(CategoryDTO categoryDTO);

    @Transactional
    CategoryDTO update(CategoryDTO categoryDTO);

    void delete(Integer id);
}
