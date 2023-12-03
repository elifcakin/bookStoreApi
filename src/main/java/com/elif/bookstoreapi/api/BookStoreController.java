package com.elif.bookstoreapi.api;


import com.elif.bookstoreapi.entity.dtos.BookStoreDTO;
import com.elif.bookstoreapi.service.abstracts.IBookStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bookStore")
public class BookStoreController {

    private final IBookStoreService bookStoreService;

    @Autowired
    public BookStoreController(IBookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(bookStoreService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(bookStoreService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody BookStoreDTO bookStoreDTO){
        return new ResponseEntity<>(bookStoreService.add(bookStoreDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BookStoreDTO bookStoreDTO){
        return new ResponseEntity<>(bookStoreService.update(bookStoreDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        bookStoreService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
