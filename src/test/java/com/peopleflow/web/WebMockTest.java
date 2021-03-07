package com.peopleflow.web;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.peopleflow.controllers.UserController;
import com.peopleflow.dto.UserDto;
import com.peopleflow.entity.UserEntity;
import com.peopleflow.enums.StateEnum;
import com.peopleflow.mappers.UserMapper;
import com.peopleflow.services.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@WebMvcTest(UserController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    private final UserDto userDto =
            new UserDto(1L, "lemilivoskodi", "Marko Milenkovic", StateEnum.ADDED.getStateName());

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(new UserEntity()));
        when(userMapper.toUserDtos(anyList()))
                .thenReturn(Collections.singletonList(userDto));
        this.mockMvc.perform(get("/user")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(userDto.getFullName())))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is(userDto.getUsername())))
                .andExpect(jsonPath("$[0].fullName", is(userDto.getFullName())))
                .andExpect(jsonPath("$[0].state", is(StateEnum.ADDED.getStateName())));
        verify(userService).getAllUsers();
		verify(userMapper).toUserDtos(anyList());
		verifyNoMoreInteractions(userMapper, userService);
    }
}