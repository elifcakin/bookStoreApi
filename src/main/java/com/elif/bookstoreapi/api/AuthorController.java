package com.elif.bookstoreapi.api;

import com.elif.bookstoreapi.entity.dtos.AuthorDTO;
import com.elif.bookstoreapi.service.abstracts.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/author")
public class AuthorController {

    private final IAuthorService authorService;

    @Autowired
    public AuthorController(IAuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AuthorDTO authorDTO){
        return new ResponseEntity<>(authorService.add(authorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody AuthorDTO authorDTO){
        return new ResponseEntity<>(authorService.update(authorDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
