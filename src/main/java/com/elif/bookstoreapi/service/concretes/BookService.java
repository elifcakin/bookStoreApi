package com.elif.bookstoreapi.service.concretes;

import com.elif.bookstoreapi.core.exceptions.ResourceNotFoundException;
import com.elif.bookstoreapi.entity.Book;
import com.elif.bookstoreapi.entity.dtos.BookDTO;
import com.elif.bookstoreapi.entity.mappers.BookMapper;
import com.elif.bookstoreapi.repository.BookRepository;
import com.elif.bookstoreapi.service.abstracts.IBookService;
import com.elif.bookstoreapi.service.validations.GenericValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookDTO> getAll(){
        List<Book> bookList = bookRepository.findAll();
        return bookMapper.toBookDTO(bookList);
    }

    @Override
    public BookDTO getById(Integer id){
        Book book = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return bookMapper.toBookDTO(book);
    }

    @Override
    @Transactional
    public BookDTO add(BookDTO bookDTO){
        Book book = bookRepository.save(bookMapper.toBook(bookDTO));
        return bookMapper.toBookDTO(book);
    }

    @Override
    @Transactional
    public BookDTO update(BookDTO bookDTO){
        GenericValidationUtils.isIdNullForUpdate(bookDTO.getId());
        Optional<Book> optionalBook = bookRepository.findById(bookDTO.getId());
        if (optionalBook.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return add(bookDTO);
    }

    @Override
    @Transactional
    public void delete(Integer id){
        Book book = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        bookRepository.delete(book);
    }
}
