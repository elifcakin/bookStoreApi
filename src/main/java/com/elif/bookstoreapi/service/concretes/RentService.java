package com.elif.bookstoreapi.service.concretes;

import com.elif.bookstoreapi.core.exceptions.GenericValidationException;
import com.elif.bookstoreapi.core.exceptions.ResourceNotFoundException;
import com.elif.bookstoreapi.entity.RentLog;
import com.elif.bookstoreapi.entity.abstracts.DailyRentReportModel;
import com.elif.bookstoreapi.entity.dtos.RentLogDTO;
import com.elif.bookstoreapi.entity.dtos.RentReportDTO;
import com.elif.bookstoreapi.entity.mappers.RentLogMapper;
import com.elif.bookstoreapi.repository.RentLogRepository;
import com.elif.bookstoreapi.service.abstracts.IRentService;
import com.elif.bookstoreapi.service.validations.GenericValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentService implements IRentService {

    private final RentLogRepository rentLogRepository;
    private final RentLogMapper rentLogMapper;

    @Autowired
    public RentService(RentLogRepository rentLogRepository, RentLogMapper rentLogMapper) {
        this.rentLogRepository = rentLogRepository;
        this.rentLogMapper = rentLogMapper;
    }

    @Override
    public List<RentLogDTO> getAll(){
        List<RentLog> rentLogList = rentLogRepository.findAll();
        return rentLogMapper.toRentLogDTO(rentLogList);
    }

    @Override
    public List<RentLogDTO> getAllByBookId(Integer bookId){
        List<RentLog> rentLogList = rentLogRepository.getAllByBook_Id(bookId);
        return rentLogMapper.toRentLogDTO(rentLogList);
    }

    @Override
    public RentLogDTO getById(Integer id){
        RentLog rentLog = rentLogRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return rentLogMapper.toRentLogDTO(rentLog);
    }

    @Override
    @Transactional
    public RentLogDTO add(RentLogDTO rentLogDTO){
        checkBookAvailableForDefinedDates(rentLogDTO);
        RentLog rentLog = rentLogRepository.save(rentLogMapper.toRentLog(rentLogDTO));
        return rentLogMapper.toRentLogDTO(rentLog);
    }

    @Override
    @Transactional
    public RentLogDTO update(RentLogDTO rentLogDTO){
        GenericValidationUtils.isIdNullForUpdate(rentLogDTO.getId());

        Optional<RentLog> optionalRentLog = rentLogRepository.findById(rentLogDTO.getId());
        if (optionalRentLog.isEmpty()){
            throw new ResourceNotFoundException();
        }

        // For date updates
        delete(rentLogDTO.getId());

        return add(rentLogDTO);
    }

    @Override
    @Transactional
    public void delete(Integer id){
        RentLog rentLog = rentLogRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        rentLogRepository.delete(rentLog);
    }

    @Override
    public RentReportDTO getDailyRentReport(LocalDate filterStartDate, LocalDate filterEndDate){

        RentReportDTO rentReportDTO = new RentReportDTO(filterStartDate,filterEndDate,new ArrayList<>());

        while (filterStartDate.compareTo(filterEndDate) <= 0){

            int rentedBookCount = rentLogRepository.countBetweenStartDateAndEndDate(filterStartDate);
            rentReportDTO.getDailyReport().add(new DailyRentReportModel(filterStartDate,rentedBookCount));

            filterStartDate = filterStartDate.plusDays(1);
        }

        return rentReportDTO;
    }

    private void checkBookAvailableForDefinedDates(RentLogDTO rentLogDTO){
        List<RentLogDTO> rentLogDTOList = getAllByBookId(rentLogDTO.getBookDTO().getId());

        LocalDate startDate = rentLogDTO.getStartDate();
        LocalDate endDate = rentLogDTO.getEndDate();

        for (RentLogDTO rentLogDTOInDb : rentLogDTOList){

            if (startDate.compareTo(rentLogDTOInDb.getStartDate()) <= 0 && endDate.compareTo(rentLogDTOInDb.getEndDate()) >= 0){
                throw new GenericValidationException("The dates you want to rent have been reserved by another user.");
            }
            if (startDate.compareTo(rentLogDTOInDb.getStartDate()) >= 0 && startDate.compareTo(rentLogDTOInDb.getEndDate()) <= 0 ){
                throw new GenericValidationException("The dates you want to rent have been reserved by another user.");
            }
            if (endDate.compareTo(rentLogDTOInDb.getStartDate()) >= 0 && endDate.compareTo(rentLogDTOInDb.getEndDate()) <= 0){
                throw new GenericValidationException("The dates you want to rent have been reserved by another user.");
            }
        }



    }

}
