package com.elif.bookstoreapi.service.concretes;

import com.elif.bookstoreapi.core.exceptions.ResourceNotFoundException;
import com.elif.bookstoreapi.entity.BookStore;
import com.elif.bookstoreapi.entity.dtos.BookStoreDTO;
import com.elif.bookstoreapi.entity.mappers.BookStoreMapper;
import com.elif.bookstoreapi.repository.BookStoreRepository;
import com.elif.bookstoreapi.service.abstracts.IBookStoreService;
import com.elif.bookstoreapi.service.validations.GenericValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookStoreService implements IBookStoreService {

    private final BookStoreRepository bookStoreRepository;
    private final BookStoreMapper bookStoreMapper;

    @Autowired
    public BookStoreService(BookStoreRepository bookStoreRepository, BookStoreMapper bookStoreMapper) {
        this.bookStoreRepository = bookStoreRepository;
        this.bookStoreMapper = bookStoreMapper;
    }

    @Override
    @Transactional
    public List<BookStoreDTO> getAll(){
        List<BookStore> bookStoreList = bookStoreRepository.findAll();
        return bookStoreMapper.toBookStoreDTO(bookStoreList);
    }

    @Override
    @Transactional
    public BookStoreDTO getById(Integer id){
        BookStore bookStore = bookStoreRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return bookStoreMapper.toBookStoreDTO(bookStore);
    }

    @Override
    @Transactional
    public BookStoreDTO add(BookStoreDTO bookStoreDTO){
        BookStore bookStore = bookStoreRepository.save(bookStoreMapper.toBookStore(bookStoreDTO));
        return bookStoreMapper.toBookStoreDTO(bookStore);
    }

    @Override
    @Transactional
    public BookStoreDTO update(BookStoreDTO bookStoreDTO){
        GenericValidationUtils.isIdNullForUpdate(bookStoreDTO.getId());

        Optional<BookStore> optionalBookStore = bookStoreRepository.findById(bookStoreDTO.getId());

        if (optionalBookStore.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return add(bookStoreDTO);
    }

    @Override
    @Transactional
    public void delete(Integer id){
        BookStore bookStore = bookStoreRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        bookStoreRepository.delete(bookStore);
    }
}
