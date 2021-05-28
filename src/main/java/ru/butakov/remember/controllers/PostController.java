package ru.butakov.remember.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import ru.butakov.remember.entity.Post;
import ru.butakov.remember.entity.User;

import java.io.IOException;

public interface PostController {
    String post(Model model);

    String add(User user, Post post, BindingResult bindingResult,Model model, MultipartFile file) throws IOException;

    String post(long id, Model model);

    String edit(long id, String text, String tag, MultipartFile file) throws IOException;

    String delete(long id);

    String userPosts(User user, Model model);
}
