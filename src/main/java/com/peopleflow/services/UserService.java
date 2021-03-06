package com.peopleflow.services;

import com.peopleflow.dao.UserDao;
import com.peopleflow.entity.UserEntity;
import com.peopleflow.enums.ExceptionMessages;
import com.peopleflow.enums.StateEnum;
import com.peopleflow.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Transactional
    public UserEntity save(UserEntity userEntity) {
        userEntity.setState(StateEnum.ACTIVE.getStateName());
        return userDao.save(userEntity);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    public UserEntity updateUserState(Long userId, String state) {
        if (state == null || state.isEmpty()
                || Stream.of(StateEnum.values()).noneMatch(v -> v.getStateName().equals(state.toUpperCase()))) {
            throw new BadRequestException(ExceptionMessages.INVALID_STATE.getMessage());
        }
        Optional<UserEntity> userEntityOptional = userDao.findById(userId);
        if (!userEntityOptional.isPresent()) {
            throw new BadRequestException(ExceptionMessages.USER_NOT_EXISTS.getMessage());
        }
        UserEntity userEntity = userEntityOptional.get();
        userEntity.setState(state);
        return userEntity;
    }

}
