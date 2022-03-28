package com.starlight.exception;

import lombok.Getter;

@Getter
public class BidInARowException extends Exception {

    private final String detail;

    public BidInARowException(String detail) {
        this.detail = detail;
    }
}
