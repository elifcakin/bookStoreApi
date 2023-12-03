package com.elif.bookstoreapi.service.concretes;

import com.elif.bookstoreapi.core.exceptions.ResourceNotFoundException;
import com.elif.bookstoreapi.entity.User;
import com.elif.bookstoreapi.entity.dtos.UserDTO;
import com.elif.bookstoreapi.entity.mappers.UserMapper;
import com.elif.bookstoreapi.repository.UserRepository;
import com.elif.bookstoreapi.service.abstracts.IUserService;
import com.elif.bookstoreapi.service.validations.GenericValidationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAll(){
        List<User> userList = userRepository.findAll();
        return userMapper.toUserDTO(userList);
    }

    @Override
    public UserDTO getById(Integer id){
        User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO add(UserDTO userDTO){
        User user = userRepository.save(userMapper.toUser(userDTO));
        return userMapper.toUserDTO(user);
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO){
        GenericValidationUtils.isIdNullForUpdate(userDTO.getId());


        getById(userDTO.getId());

        return add(userDTO);
    }

    @Override
    @Transactional
    public void delete(Integer id){
        User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        userRepository.delete(user);
    }

}
