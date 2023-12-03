package com.elif.bookstoreapi.entity.mappers;

import com.elif.bookstoreapi.entity.BookStore;
import com.elif.bookstoreapi.entity.dtos.BookStoreDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookStoreMapper {

    @Named("toBookStoreDTO")
    BookStoreDTO toBookStoreDTO(BookStore bookStore);
    @Named("toBookStore")
    BookStore toBookStore(BookStoreDTO bookStoreDTO);
    @IterableMapping(qualifiedByName = "toBookStoreDTO")
    List<BookStoreDTO> toBookStoreDTO(Collection<BookStore> bookStoreCollection);
    @IterableMapping(qualifiedByName = "toBookStore")
    List<BookStore> toBookStore(Collection<BookStoreDTO> bookStoreDTO);

}
