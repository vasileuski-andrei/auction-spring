package com.starlight.service;

import java.util.List;

public interface CommonService<T, V> {

    void create(T model);

    T findById(V value);

    T update(T model);

    void delete(V value);

    List<T> getAll();
}
