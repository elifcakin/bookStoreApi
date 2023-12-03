package com.elif.bookstoreapi.entity.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Integer id;

    private String name;

    private CategoryDTO categoryDTO;

    private AuthorDTO authorDTO;

    private BookStoreDTO bookStoreDTO;

}
