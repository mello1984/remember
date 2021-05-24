package ru.butakov.remember.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.butakov.remember.entity.User;

public interface RegistrationController {
    @GetMapping("/registration")
    String registration();

    @PostMapping("/registration")
    String registration(User user, Model model);

    @GetMapping("/activate/{code}")
    String activate(Model model, @PathVariable("code") String code);
}
