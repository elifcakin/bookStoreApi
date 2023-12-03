package com.elif.bookstoreapi.service.concretes;

import com.elif.bookstoreapi.core.exceptions.ResourceNotFoundException;
import com.elif.bookstoreapi.entity.Category;
import com.elif.bookstoreapi.entity.dtos.CategoryDTO;
import com.elif.bookstoreapi.entity.mappers.CategoryMapper;
import com.elif.bookstoreapi.repository.CategoryRepository;
import com.elif.bookstoreapi.service.abstracts.ICategoryService;
import com.elif.bookstoreapi.service.validations.GenericValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAll(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryMapper.toCategoryDTO(categoryList);
    }

    @Override
    public CategoryDTO getById(Integer id){
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return categoryMapper.toCategoryDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO add(CategoryDTO categoryDTO){
        Category category = categoryRepository.save(categoryMapper.toCategory(categoryDTO));
        return categoryMapper.toCategoryDTO(category);
    }

    @Override
    @Transactional
    public CategoryDTO update(CategoryDTO categoryDTO) {

        GenericValidationUtils.isIdNullForUpdate(categoryDTO.getId());

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getId());

        if (optionalCategory.isEmpty()) {
            throw new ResourceNotFoundException();
        }


        return add(categoryDTO);

    }

    @Override
    @Transactional
    public void delete(Integer id){
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        categoryRepository.delete(category);
    }
}
