package com.elif.bookstoreapi.api;

import com.elif.bookstoreapi.entity.dtos.RentLogDTO;
import com.elif.bookstoreapi.service.abstracts.IRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("api/rent")
public class RentController {

    private final IRentService rentService;

    @Autowired
    public RentController(IRentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(rentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(rentService.getById(id));
    }

    @GetMapping("/getRentReport")
    public ResponseEntity<?> getRentReport(@RequestParam(name = "startDate") LocalDate startDate,
                                           @RequestParam(name = "endDate") LocalDate endDate){
        return ResponseEntity.ok(rentService.getDailyRentReport(startDate,endDate));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody RentLogDTO rentLogDTO){
        return new ResponseEntity<>(rentService.add(rentLogDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody RentLogDTO rentLogDTO){
        return new ResponseEntity<>(rentService.update(rentLogDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        rentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
