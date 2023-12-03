package com.elif.bookstoreapi.entity.mappers;

import com.elif.bookstoreapi.entity.RentLog;
import com.elif.bookstoreapi.entity.dtos.RentLogDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",uses = {BookMapper.class,UserMapper.class})
public interface RentLogMapper {

    @Named("toRentLog")
    @Mapping(source = "bookDTO",target = "book",qualifiedByName = "toBook")
    @Mapping(source = "userDTO",target = "user",qualifiedByName = "toUser")
    RentLog toRentLog(RentLogDTO rentLogDTO);

    @Named("toRentLogDTO")
    @Mapping(target = "bookDTO",source = "book",qualifiedByName = "toBookDTO")
    @Mapping(target = "userDTO",source = "user",qualifiedByName = "toUserDTO")
    RentLogDTO toRentLogDTO(RentLog rentLog);

    @IterableMapping(qualifiedByName = "toRentLog")
    List<RentLog> toRentLog(Collection<RentLogDTO> rentLogDTOCollection);
    @IterableMapping(qualifiedByName = "toRentLogDTO")
    List<RentLogDTO> toRentLogDTO(Collection<RentLog> rentLogCollection);


}
