package ru.butakov.remember.controllers;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.butakov.remember.entity.User;

import java.io.IOException;

public interface PostController {
    String records(Model model);

    String addRecord(User user, String text, String tag, MultipartFile file) throws IOException;

    String records(long id, Model model);

    String editRecord(long id, String text, String tag, MultipartFile file) throws IOException;

    String deleteRecord(long id);
}
