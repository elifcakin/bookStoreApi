package com.elif.bookstoreapi.entity.mappers;

import com.elif.bookstoreapi.entity.Author;
import com.elif.bookstoreapi.entity.dtos.AuthorDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    @Named("toAuthor")
    Author toAuthor(AuthorDTO authorDTO);

    @Named("toAuthorDTO")
    AuthorDTO toAuthorDTO(Author author);

    @IterableMapping(qualifiedByName = "toAuthor")
    List<Author> toAuthor(Collection<AuthorDTO> authorDTOCollection);

    @IterableMapping(qualifiedByName = "toAuthorDTO")
    List<AuthorDTO> toAuthorDTO(Collection<Author> authorDTOCollection);

}
