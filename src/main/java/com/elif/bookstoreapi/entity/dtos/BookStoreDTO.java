package com.elif.bookstoreapi.entity.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookStoreDTO {

    private Integer id;

    private String name;

    private String address;

    private String phone;

}
