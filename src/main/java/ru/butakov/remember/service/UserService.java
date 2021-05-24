package ru.butakov.remember.service;

import ru.butakov.remember.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    Optional<User> findById(int id);

    boolean addUser(User user);
}
