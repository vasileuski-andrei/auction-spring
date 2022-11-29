package com.starlight.auction.exception;

import lombok.Getter;

@Getter
public class ValidationException extends Exception {

    private final String detail;

    public ValidationException(String detail) {
        this.detail = detail;
    }

}
