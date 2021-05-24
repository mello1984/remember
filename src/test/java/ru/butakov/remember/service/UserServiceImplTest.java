package ru.butakov.remember.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.butakov.remember.dao.UserRepository;
import ru.butakov.remember.entity.Role;
import ru.butakov.remember.entity.User;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/generate-db-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/generate-db-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class UserServiceImplTest {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void addNewUser() {
        User user = new User("Mike", "mike", Collections.singleton(Role.USER));
        boolean result = userService.addUser(user);
        assertTrue(result);
        assertTrue(user.isActive());
    }

    @Test
    void addExistingUser() {
        User user = new User("admin", "123", Collections.singleton(Role.USER));
        boolean result = userService.addUser(user);
        assertFalse(result);
    }
}