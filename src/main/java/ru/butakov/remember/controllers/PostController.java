package ru.butakov.remember.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import ru.butakov.remember.entity.Post;
import ru.butakov.remember.entity.User;

import java.io.IOException;

public interface PostController {
//    String index(Model model);

    String edit(User user, long id, Model model);

    String addPost(User user, Post post, BindingResult bindingResult, Model model, MultipartFile file) throws IOException;

    String updatePost(User user, Post post, BindingResult bindingResult, long id, Model model, MultipartFile file) throws IOException;

    String deletePost(User user, long id);

    String userPosts(int user, Model model);

    String filterByTag(String tag, Model model);
}
