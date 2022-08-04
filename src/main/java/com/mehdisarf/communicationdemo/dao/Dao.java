package com.mehdisarf.communicationdemo.dao;

import java.util.List;

public interface Dao<T> {

    T get(long id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    void delete(long id);
}
