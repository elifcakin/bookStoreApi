package com.elif.bookstoreapi.entity.mappers;

import com.elif.bookstoreapi.entity.Category;
import com.elif.bookstoreapi.entity.dtos.CategoryDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Named("toCategoryDTO")
    CategoryDTO toCategoryDTO(Category category);
    @Named("toCategory")
    Category toCategory(CategoryDTO categoryDTO);

    @IterableMapping(qualifiedByName = "toCategoryDTO")
    List<CategoryDTO> toCategoryDTO(Collection<Category> categoryCollection);
    @IterableMapping(qualifiedByName = "toCategory")
    List<Category> toCategory(Collection<CategoryDTO> categoryDTOCollection);

}
