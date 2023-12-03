package com.elif.bookstoreapi.repository;

import com.elif.bookstoreapi.entity.RentLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public interface RentLogRepository extends JpaRepository<RentLog,Integer> {

    List<RentLog> getAllByBook_Id(Integer bookId);

    @Query("SELECT COUNT(*) FROM RentLog WHERE :givenDate >= startDate AND :givenDate <= endDate")
    int countBetweenStartDateAndEndDate(@Param("givenDate") LocalDate givenDate);
}
