package ru.butakov.remember.controllers;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/generate-db-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/generate-db-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserService userService;

    @Test
    public void getUserListTestNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"))
        ;
    }

    @Test
    @WithUserDetails("admin")
    public void getUserListTestAdminAccessSuccessful() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='user_table']").exists());
    }

    @Test
    @WithUserDetails("user")
    public void getUserListTestUserAccessDenied() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin")
    public void getEditUserPageNotExistingUser() throws Exception {
        mockMvc.perform(get("/users/100"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    @WithUserDetails("user")
    public void getEditUserPageUserAccessDenied() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin")
    public void getEditUserPageAccessSuccessful() throws Exception {
        mockMvc.perform(get("/users/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("user")));
    }

    @Test
    @WithUserDetails("admin")
    public void editUser() throws Exception {
        mockMvc.perform(post("/users/")
                .param("userId", "2")
                .param("username", "Joe")
                .param("active", "false")
                .param("USER", "")
                .param("MODERATOR", "")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        Optional<User> userFromDb = userService.findById(2);
        assertTrue(userFromDb.isPresent());

        User user = userFromDb.get();
        assertAll(
                () -> assertEquals("Joe", user.getUsername()),
                () -> assertFalse(user.isActive()),
                () -> assertEquals(Set.of(Role.USER, Role.MODERATOR), user.getRoles())
        );
    }

}