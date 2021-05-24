package ru.butakov.remember.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.butakov.remember.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByActivationCode(String code);
}
