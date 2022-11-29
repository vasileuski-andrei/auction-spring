package com.starlight.service;

import com.starlight.exception.UserAlreadyExistException;
import com.starlight.exception.ValidationException;

public interface CommonService<T> {

    void create(T t) throws ValidationException, UserAlreadyExistException;

}
