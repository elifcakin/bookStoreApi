package com.elif.bookstoreapi.service.concretes;

import com.elif.bookstoreapi.core.exceptions.ResourceNotFoundException;
import com.elif.bookstoreapi.entity.Author;
import com.elif.bookstoreapi.entity.dtos.AuthorDTO;
import com.elif.bookstoreapi.entity.mappers.AuthorMapper;
import com.elif.bookstoreapi.repository.AuthorRepository;
import com.elif.bookstoreapi.service.abstracts.IAuthorService;
import com.elif.bookstoreapi.service.validations.GenericValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements IAuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<AuthorDTO> getAll() {
        List<Author> authorList = authorRepository.findAll();
        return authorMapper.toAuthorDTO(authorList);
    }

    @Override
    public AuthorDTO getById(Integer id) {
        Author author = authorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return authorMapper.toAuthorDTO(author);
    }

    @Override
    @Transactional
    public AuthorDTO add(AuthorDTO authorDTO) {
        Author author = authorRepository.save(authorMapper.toAuthor(authorDTO));
        return authorMapper.toAuthorDTO(author);
    }



    @Override
    @Transactional
    public AuthorDTO update(AuthorDTO authorDTO) {

        GenericValidationUtils.isIdNullForUpdate(authorDTO.getId());

        Optional<Author> optionalAuthor = authorRepository.findById(authorDTO.getId());

        if (optionalAuthor.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        Author author = authorRepository.save(authorMapper.toAuthor(authorDTO));
        return authorMapper.toAuthorDTO(author);

    }

    @Override
    @Transactional
    public void delete(Integer id){
        Author author = authorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        authorRepository.delete(author);
    }
}
