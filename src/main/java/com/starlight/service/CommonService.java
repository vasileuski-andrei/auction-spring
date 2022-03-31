package com.starlight.service;

import com.starlight.exception.UserAlreadyExistException;
import com.starlight.exception.ValidationException;

import java.util.List;

public interface CommonService<T, V> {

    void create(T model) throws ValidationException, UserAlreadyExistException;

    T findById(V value);

    T update(T model);

    void delete(V value);

    List<T> getAll();
}
