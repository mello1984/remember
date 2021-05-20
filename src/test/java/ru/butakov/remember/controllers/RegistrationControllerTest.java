package ru.butakov.remember.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.butakov.remember.entity.Role;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.service.UserService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/generate-db-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/generate-db-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    public void registrationUserTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "Mike")
                .param("password", "mike")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());


        Optional<User> userFromDb = userService.findByUsername("Mike");
        assertTrue(userFromDb.isPresent());

        User user = userFromDb.get();
        assertAll(
                () -> assertEquals(10, user.getId()),
                () -> assertEquals("Mike", user.getUsername()),
                () -> assertTrue(user.isActive()),
                () -> assertEquals(Collections.singleton(Role.USER), user.getRoles()),
                () -> assertTrue(user.getRecordList().isEmpty())
        );
    }

    @Test
    public void registrationExistingUserExceptionTest() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "admin")
                .param("password", "123")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        Optional<User> userFromDb = userService.findByUsername("admin");
        assertTrue(userFromDb.isPresent());

        User user = userFromDb.get();
        assertAll(
                () -> assertEquals(1, user.getId()),
                () -> assertEquals("admin", user.getUsername()),
                () -> assertTrue(user.isActive()),
                () -> assertEquals(Set.of(Role.ADMIN, Role.USER), user.getRoles()),
                () -> assertEquals(3, user.getRecordList().size())
        );
    }

}

