package ru.butakov.remember.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.butakov.remember.service.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({"/application-test.properties", "/private-test.properties"})
@Sql(value = {"/generate-db-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/generate-db-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void registrationGetTest() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(xpath("//*[@id='register-form']").exists());
    }

    @Test
    public void registrationNewUserTest() throws Exception {

        Mockito.when(userService.addUser(Mockito.any())).thenReturn(true);

        this.mockMvc.perform(post("/registration")
                .param("username", "Mike")
                .param("password", "mike")
                .param("passwordConfirm", "mike")
                .param("email", "some@some.com")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void registrationExistingUserExceptionTest() throws Exception {

        Mockito.when(userService.addUser(Mockito.any())).thenReturn(false);

        this.mockMvc.perform(post("/registration")
                .param("username", "admin")
                .param("password", "123")
                .param("passwordConfirm", "123")
                .param("email", "some@some.com")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("User with this name already exists.")));
    }

    @Test
    public void activateExistingCodeTest() throws Exception{
        Mockito.when(userService.activateEmail("123")).thenReturn(true);
        this.mockMvc.perform(get("/activate/123"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Email activated")));
    }  @Test
    public void activateNotExistingCodeTest() throws Exception{
        Mockito.when(userService.activateEmail("123")).thenReturn(false);
        this.mockMvc.perform(get("/activate/123"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Activation code is not found")));
    }

}

