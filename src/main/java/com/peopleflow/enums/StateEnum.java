package com.peopleflow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StateEnum {

    ADDED("ADDED"),
    IN_CHECK("IN-CHECK"),
    APPROVED("APPROVED"),
    ACTIVE("ACTIVE");

    private final String stateName;

}
