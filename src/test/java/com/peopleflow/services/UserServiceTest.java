package com.peopleflow.services;

import com.peopleflow.dao.UserDao;
import com.peopleflow.entity.UserEntity;
import com.peopleflow.enums.ExceptionMessages;
import com.peopleflow.enums.StateEnum;
import com.peopleflow.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    private static final long ID = 1L;

    @Test
    void saveTest() {
        UserEntity userEntity = prepareUserEntity();

        when(userDao.save(userEntity)).thenAnswer(mock -> {
            UserEntity entity1 = mock.getArgument(0);
            entity1.setId(ID);
            return entity1;
        });

        UserEntity savedUserEntity = userService.save(userEntity);
        assertEquals(userEntity.getId(), savedUserEntity.getId());
        assertEquals(userEntity.getFullName(), savedUserEntity.getFullName());
        assertEquals(userEntity.getUsername(), savedUserEntity.getUsername());
        assertEquals(StateEnum.ACTIVE.getStateName(), savedUserEntity.getState());
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void updateUserStateTest() {
        UserEntity userEntity = prepareUserEntity();

        when(userDao.findById(ID)).thenReturn(Optional.of(userEntity));

        UserEntity savedUserEntity = userService.updateUserState(ID, StateEnum.APPROVED.getStateName());
        assertEquals(userEntity.getId(), savedUserEntity.getId());
        assertEquals(userEntity.getFullName(), savedUserEntity.getFullName());
        assertEquals(userEntity.getUsername(), savedUserEntity.getUsername());
        assertEquals(StateEnum.APPROVED.getStateName(), savedUserEntity.getState());
        verifyNoMoreInteractions(userDao);
    }

    @Test
    void updateUserStateTestNullState() {
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> userService.updateUserState(ID, null)
        );

        assertEquals(thrown.getMessage(), ExceptionMessages.INVALID_STATE.getMessage());
        verifyNoInteractions(userDao);
    }

    @ParameterizedTest
    @ValueSource(strings = {"UNKNOWN"})
    @NullAndEmptySource
    void updateUserStateTestUnknownState(String state) {
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> userService.updateUserState(ID, state)
        );

        assertEquals(thrown.getMessage(), ExceptionMessages.INVALID_STATE.getMessage());
        verifyNoInteractions(userDao);
    }

    @Test
    void updateUserStateTestUserNotExists() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> userService.updateUserState(ID, StateEnum.APPROVED.getStateName())
        );

        assertEquals(thrown.getMessage(), ExceptionMessages.USER_NOT_EXISTS.getMessage());
        verifyNoMoreInteractions(userDao);
    }

    private UserEntity prepareUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName("Marko Milenkovic");
        userEntity.setUsername("lemilivoskodi");
        return userEntity;
    }

}
