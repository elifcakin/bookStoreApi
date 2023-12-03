package com.elif.bookstoreapi.service.abstracts;

import com.elif.bookstoreapi.entity.dtos.RentLogDTO;
import com.elif.bookstoreapi.entity.dtos.RentReportDTO;

import java.time.LocalDate;
import java.util.List;

public interface IRentService {
    List<RentLogDTO> getAll();

    List<RentLogDTO> getAllByBookId(Integer bookId);

    RentLogDTO getById(Integer id);

    RentLogDTO add(RentLogDTO rentLogDTO);

    RentLogDTO update(RentLogDTO rentLogDTO);

    void delete(Integer id);

    RentReportDTO getDailyRentReport(LocalDate filterStartDate, LocalDate filterEndDate);
}
