package com.elif.bookstoreapi.entity.abstracts;

import com.elif.bookstoreapi.entity.abstracts.TimeFilterReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyRentReportModel {

    private LocalDate date;
    private int rentedBookCount;

}
