package com.elif.bookstoreapi.entity.dtos;

import com.elif.bookstoreapi.entity.abstracts.DailyRentReportModel;
import com.elif.bookstoreapi.entity.abstracts.TimeFilterReport;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RentReportDTO  {


    private LocalDate filterEndDate;
    private LocalDate filterStartDate;
    private List<DailyRentReportModel> dailyReport = new ArrayList<>();


    public RentReportDTO(LocalDate filterEndDate, LocalDate filterStartDate, List<DailyRentReportModel> dailyReport) {
        this.filterEndDate = filterEndDate;
        this.filterStartDate = filterStartDate;
        this.dailyReport = dailyReport;
    }

    public RentReportDTO() {
    }

    public LocalDate getFilterEndDate() {
        return filterEndDate;
    }

    public void setFilterEndDate(LocalDate filterEndDate) {
        this.filterEndDate = filterEndDate;
    }

    public LocalDate getFilterStartDate() {
        return filterStartDate;
    }

    public void setFilterStartDate(LocalDate filterStartDate) {
        this.filterStartDate = filterStartDate;
    }

    public List<DailyRentReportModel> getDailyReport() {
        return dailyReport;
    }

    public void setDailyReport(List<DailyRentReportModel> dailyReport) {
        this.dailyReport = dailyReport;
    }
}
