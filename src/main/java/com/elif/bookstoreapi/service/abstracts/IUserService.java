package com.elif.bookstoreapi.service.abstracts;

import com.elif.bookstoreapi.entity.dtos.UserDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAll();

    UserDTO getById(Integer id);

    @Transactional
    UserDTO add(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    void delete(Integer id);
}
