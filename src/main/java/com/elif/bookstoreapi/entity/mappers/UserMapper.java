package com.elif.bookstoreapi.entity.mappers;

import com.elif.bookstoreapi.entity.User;
import com.elif.bookstoreapi.entity.dtos.UserDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("toUserDTO")
    UserDTO toUserDTO(User user);
    @Named("toUser")
    User toUser(UserDTO userDTO);
    @IterableMapping(qualifiedByName = "toUser")
    List<User> toUser(Collection<UserDTO> userDTOCollection);
    @IterableMapping(qualifiedByName = "toUserDTO")
    List<UserDTO> toUserDTO(Collection<User> userCollection);
}
