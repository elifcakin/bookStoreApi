package com.elif.bookstoreapi.entity.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeFilterReport {
    private LocalDate filterStartDate;
    private LocalDate filterEndDate;
}
