package com.peopleflow.controllers;

import com.peopleflow.dto.ErrorResponse;
import com.peopleflow.dto.StateDto;
import com.peopleflow.dto.UserDto;
import com.peopleflow.entity.UserEntity;
import com.peopleflow.mappers.UserMapper;
import com.peopleflow.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get all users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get all users",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))
    })
    @GetMapping
    public List<UserDto> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return userMapper.toUserDtos(users);
    }

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Register new user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
    })
    @PostMapping
    public UserDto registerUser(@RequestBody UserDto user) {
        UserEntity userEntity = userMapper.toUserEntity(user);
        UserEntity savedUser = userService.save(userEntity);
        return userMapper.toUserDto(savedUser);
    }

    @Operation(summary = "Update state for user with given id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update state for user with id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "State is not valid or User with given id does not exists!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    @PutMapping(value = "{userId}")
    public UserDto updateUserState(@PathVariable Long userId, @RequestBody StateDto state) {
        UserEntity savedUser = userService.updateUserState(userId, state.getState());
        return userMapper.toUserDto(savedUser);
    }

}
