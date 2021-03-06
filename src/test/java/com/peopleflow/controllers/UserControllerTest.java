package com.peopleflow.controllers;

import com.peopleflow.dto.StateDto;
import com.peopleflow.dto.UserDto;
import com.peopleflow.entity.UserEntity;
import com.peopleflow.mappers.UserMapper;
import com.peopleflow.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;


    @Test
    void registerUserTest() {
        UserDto userDto = prepareUserDto();
        UserEntity userEntity = new UserEntity();

        when(userMapper.toUserEntity(userDto)).thenReturn(userEntity);
        when(userService.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.toUserDto(userEntity)).thenReturn(userDto);

        UserDto savedUser = userController.registerUser(userDto);
        assertEquals(userDto, savedUser);
        verifyNoMoreInteractions(userService, userMapper);
    }

    @Test
    void updateUserStateTest() {
        UserDto userDto = prepareUserDto();
        when(userMapper.toUserDto(any(UserEntity.class))).thenReturn(userDto);
        when(userService.updateUserState(anyLong(), anyString())).thenReturn(new UserEntity());

        UserDto savedUser = userController.updateUserState(1L, new StateDto("state"));
        assertEquals(userDto, savedUser);
    }

    private UserDto prepareUserDto() {
        UserDto userDto = new UserDto();
        userDto.setFullName("Marko Milenkovic");
        userDto.setUsername("lemilivoskodi");
        return userDto;
    }

}
