package ru.butakov.remember.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.butakov.remember.dao.UserRepository;
import ru.butakov.remember.entity.Role;
import ru.butakov.remember.entity.User;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({"/application-test.properties", "/private-test.properties"})
@Sql(value = {"/generate-db-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/generate-db-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class UserServiceImplTest {
    @Autowired
    UserServiceImpl userService;
    @MockBean
    MailSender mailSender;
    @MockBean
    UserRepository userRepository;

    @Test
    void addNewUserNoEmail() {
        User user = new User("Mike", "mike", Collections.singleton(Role.USER));

        boolean result = userService.addUser(user);

        assertTrue(result);
        assertTrue(user.isActive());
        Mockito.verifyNoInteractions(mailSender);
    }

    @Test
    void addNewUserWithEmail() {
        User user = new User("Mike", "mike", Collections.singleton(Role.USER));
        user.setEmail("e@mail");

        boolean result = userService.addUser(user);

        assertTrue(result);
        assertTrue(user.isActive());
        Mockito.verify(mailSender, Mockito.times(1))
                .send(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verifyNoMoreInteractions(mailSender);
    }

    @Test
    void addExistingUser() {
        User user = new User("admin", "123", Collections.singleton(Role.USER));
        Mockito.when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));

        assertFalse(userService.addUser(user));
        Mockito.verifyNoInteractions(mailSender);
    }

    @Test
    void activateEmailExistingCode() {
        User user = new User();
        user.setActivationCode("code");

        Mockito.when(userRepository.findByActivationCode("123")).thenReturn(Optional.of(user));

        assertTrue(userService.activateEmail("123"));
        assertNull(user.getActivationCode());
        Mockito.verify(userRepository, Mockito.times(1)).findByActivationCode("123");
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userRepository);
    }

    @Test
    void activateEmailNotExistingCode() {
        Mockito.when(userRepository.findByActivationCode("123")).thenReturn(Optional.empty());

        assertFalse(userService.activateEmail("123"));
        Mockito.verify(userRepository, Mockito.times(1)).findByActivationCode("123");
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userRepository);
    }
}