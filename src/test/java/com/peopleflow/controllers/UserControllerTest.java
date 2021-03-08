package com.peopleflow.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peopleflow.dto.StateDto;
import com.peopleflow.dto.UserDto;
import com.peopleflow.entity.UserEntity;
import com.peopleflow.enums.ExceptionMessages;
import com.peopleflow.enums.StateEnum;
import com.peopleflow.mappers.UserMapper;
import com.peopleflow.services.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;

    private final UserDto userDto =
            new UserDto(1L, "lemilivoskodi", "Marko Milenkovic", StateEnum.ADDED.getStateName());

    @Test
    public void getAllUsersTest() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(new UserEntity()));
        when(userMapper.toUserDtos(anyList()))
                .thenReturn(Collections.singletonList(userDto));
        this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
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

    @Test
    public void registerUserTestSuccess() throws Exception {
		UserEntity userEntity =
				new UserEntity(1L, "lemilivoskodi", "Marko Milenkovic", StateEnum.ADDED.getStateName());
        when(userMapper.toUserEntity(any())).thenReturn(userEntity);
        when(userService.save(any())).thenReturn(userEntity);
        when(userMapper.toUserDto(any())).thenReturn(userDto);
        this.mockMvc.perform(
                post("/user").content(objectMapper.writeValueAsString(userDto)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(userDto.getFullName())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is(userDto.getUsername())))
                .andExpect(jsonPath("$.fullName", is(userDto.getFullName())))
                .andExpect(jsonPath("$.state", is(StateEnum.ADDED.getStateName())));
        verify(userService).save(any());
        verify(userMapper).toUserEntity(any());
        verify(userMapper).toUserDto(any());
        verifyNoMoreInteractions(userMapper, userService);
    }

	@Test
	public void updateUserStateTestIllegalState() throws Exception {
		StateDto stateDto = new StateDto("some state");
		when(userService.updateUserState(anyLong(), any())).thenCallRealMethod();
		this.mockMvc.perform(
				patch("/user/1").content(objectMapper.writeValueAsString(stateDto)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is(ExceptionMessages.INVALID_STATE.getMessage())));
		verify(userService).updateUserState(anyLong(), any());
		verifyNoInteractions(userMapper);
		verifyNoMoreInteractions(userMapper, userService);
	}


	@Test
	public void updateUserStateTestSuccess() throws Exception {
		StateDto stateDto = new StateDto(StateEnum.ADDED.getStateName());
		UserEntity userEntity =
				new UserEntity(1L, "lemilivoskodi", "Marko Milenkovic", StateEnum.ADDED.getStateName());
		when(userService.updateUserState(anyLong(), any())).thenReturn(userEntity);
		when(userMapper.toUserDto(any())).thenReturn(userDto);
		this.mockMvc.perform(
				patch("/user/1").content(objectMapper.writeValueAsString(stateDto)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.username", is(userDto.getUsername())))
				.andExpect(jsonPath("$.fullName", is(userDto.getFullName())))
				.andExpect(jsonPath("$.state", is(StateEnum.ADDED.getStateName())));
		verify(userService).updateUserState(anyLong(), any());
		verify(userMapper).toUserDto(any());
		verifyNoMoreInteractions(userMapper, userService);
	}

}