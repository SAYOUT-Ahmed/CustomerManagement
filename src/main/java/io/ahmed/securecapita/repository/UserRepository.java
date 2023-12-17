package io.ahmed.securecapita.repository;

import io.ahmed.securecapita.model.User;

import java.util.Collection;

public interface UserRepository<T extends User> {
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);
}
