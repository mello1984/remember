package ru.butakov.remember.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.butakov.remember.dao.PostRepository;
import ru.butakov.remember.entity.Post;
import ru.butakov.remember.exceptions.NotFoundException;
import ru.butakov.remember.exceptions.SecurityException;
import ru.butakov.remember.service.PostService;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    @MockBean
    private PostService postService;

    @Test
    @WithUserDetails
    public void indexTest() throws Exception {
        Mockito.when(postService.findAll()).thenReturn(postRepository.findAll());

        this.mockMvc
                .perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(4));

        Mockito.verify(postService).findAll();
        Mockito.verifyNoMoreInteractions(postService);
    }

    @Test
    @WithUserDetails
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

        Mockito.verify(postService).save(Mockito.any(Post.class));
    }

    @Test
    @WithUserDetails
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
    }


    @Test
    @WithUserDetails("admin")
    public void deletePostSuccessfulTest() throws Exception {
        Mockito.when(postService.findById(1)).thenReturn(postRepository.findById(1L));

        mockMvc.perform(delete("/posts/1").with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));

        Mockito.verify(postService).delete(Mockito.any(Post.class));
        Mockito.verify(postService).findById(1L);
        Mockito.verifyNoMoreInteractions(postService);
    }

    @Test
    @WithUserDetails("admin")
    public void deleteNotExistingPostTest() throws Exception {
        Mockito.when(postService.findById(12)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/posts/12").with(csrf()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

        Mockito.verify(postService, Mockito.never()).delete(Mockito.any(Post.class));
        Mockito.verify(postService).findById(12L);
        Mockito.verifyNoMoreInteractions(postService);
    }

    @Test
    @WithUserDetails("admin")
    public void deleteNotOwnPostTest() throws Exception {
        Mockito.when(postService.findById(4)).thenReturn(postRepository.findById(4L));

        mockMvc.perform(delete("/posts/4").with(csrf()))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof SecurityException));

        Mockito.verify(postService, Mockito.never()).delete(Mockito.any(Post.class));
        Mockito.verify(postService).findById(4L);
        Mockito.verifyNoMoreInteractions(postService);
    }
}