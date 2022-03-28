package com.starlight.exception;

import lombok.Getter;

@Getter
public class OwnerBidException extends Exception {

    private final String detail;

    public OwnerBidException(String detail) {
        this.detail = detail;
    }
}
