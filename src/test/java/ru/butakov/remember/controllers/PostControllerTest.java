package ru.butakov.remember.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import ru.butakov.remember.dao.PostRepository;
import ru.butakov.remember.entity.Post;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource({"/application-test.properties", "/private-test.properties"})
@Sql(value = {"/generate-db-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/generate-db-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PostRepository postRepository;

    @Test
    @WithUserDetails("admin")
    public void postListAdminTest() throws Exception {
        this.mockMvc
                .perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(4));
    }

    @Test
    @WithUserDetails("user")
    public void postListUserTest() throws Exception {
        this.mockMvc
                .perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(4));
    }

    @Test
    @WithUserDetails("admin")
    public void addPostSuccessfulWithFileTest() throws Exception {
        String text = "my-text";
        String tag = "my-tag";

        MockMultipartFile mockMultipartFile = new MockMultipartFile("name", "content".getBytes(StandardCharsets.UTF_8));
        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/posts")
                        .file("file", mockMultipartFile.getBytes())
                        .param("text", text)
                        .param("tag", tag)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"))
                .andExpect(authenticated());

        Optional<Post> actual = postRepository.findAll().stream()
                .filter(r -> r.getText().equals("my-text")).findFirst();

        assertTrue(actual.isPresent());
        assertEquals(text, actual.get().getText());
        assertEquals(tag, actual.get().getTag());
        assertFalse(actual.get().getFilename().isEmpty());
    }

    @Test
    @WithUserDetails("admin")
    public void addPostSuccessfulWithoutFileAdminTest() throws Exception {
        String text = "my-text";
        String tag = "my-tag";

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/posts")
                        .param("text", text)
                        .param("tag", tag)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"))
                .andExpect(authenticated());

        Optional<Post> actual = postRepository.findAll().stream()
                .filter(r -> r.getText().equals("my-text")).findFirst();

        assertTrue(actual.isPresent());
        assertEquals(text, actual.get().getText());
        assertEquals(tag, actual.get().getTag());
        assertNull(actual.get().getFilename());
    }

    @Test
    @WithUserDetails("user")
    public void addPostSuccessfulWithoutFileUserTest() throws Exception {
        String text = "my-text";
        String tag = "my-tag";

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/posts")
                        .param("text", text)
                        .param("tag", tag)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"))
                .andExpect(authenticated());

        Optional<Post> actual = postRepository.findAll().stream()
                .filter(r -> r.getText().equals("my-text")).findFirst();

        assertTrue(actual.isPresent());
        assertEquals(text, actual.get().getText());
        assertEquals(tag, actual.get().getTag());
        assertNull(actual.get().getFilename());
    }

}