package com.peopleflow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExceptionMessages {

    INVALID_STATE("State is not valid!"),
    USER_NOT_EXISTS("User with given id does not exists!");

    private final String message;

}
