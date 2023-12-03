package com.elif.bookstoreapi.entity.mappers;

import com.elif.bookstoreapi.entity.Book;
import com.elif.bookstoreapi.entity.dtos.BookDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",uses = {BookStoreMapper.class,CategoryMapper.class,AuthorMapper.class})
public interface BookMapper {

    @Named("toBook")
    @Mapping(source = "bookStoreDTO",target = "bookStore",qualifiedByName = "toBookStore")
    @Mapping(source = "categoryDTO", target = "category",qualifiedByName = "toCategory")
    @Mapping(source = "authorDTO",target = "author",qualifiedByName = "toAuthor")
    Book toBook(BookDTO bookDTO);

    @Named("toBookDTO")
    @Mapping(target = "bookStoreDTO",source = "bookStore",qualifiedByName = "toBookStoreDTO")
    @Mapping(target = "categoryDTO", source = "category",qualifiedByName = "toCategoryDTO")
    @Mapping(target = "authorDTO",source = "author",qualifiedByName = "toAuthorDTO")
    BookDTO toBookDTO(Book book);

    @IterableMapping(qualifiedByName = "toBook")
    List<Book> toBook(Collection<BookDTO> bookDTOCollection);

    @IterableMapping(qualifiedByName = "toBookDTO")
    List<BookDTO> toBookDTO(Collection<Book> bookCollection);

}
