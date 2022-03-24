package com.starlight.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistException extends Exception {

    private final String detail;

    public UserAlreadyExistException(String detail) {
        this.detail = detail;
    }


}
