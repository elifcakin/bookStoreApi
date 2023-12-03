package com.elif.bookstoreapi.entity.dtos;

import com.elif.bookstoreapi.entity.Book;
import com.elif.bookstoreapi.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentLogDTO {


    private Integer id;

    private BookDTO bookDTO;

    private UserDTO userDTO;

    private LocalDate startDate;

    private LocalDate endDate;

}
