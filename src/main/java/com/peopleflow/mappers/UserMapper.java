package com.peopleflow.mappers;

import com.peopleflow.dto.UserDto;
import com.peopleflow.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(UserEntity userEntity);

    UserEntity toUserEntity(UserDto userDto);

    List<UserDto> toUserDtos(List<UserEntity> users);
}
