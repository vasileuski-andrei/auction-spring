package com.starlight.auction.service;

import com.starlight.auction.exception.UserAlreadyExistException;
import com.starlight.auction.exception.ValidationException;

public interface CommonService<T> {

    void create(T t) throws ValidationException, UserAlreadyExistException;

}
