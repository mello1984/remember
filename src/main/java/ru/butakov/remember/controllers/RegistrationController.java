package ru.butakov.remember.controllers;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.butakov.remember.entity.User;

public interface RegistrationController {
    @GetMapping("/registration")
    String getRegistrationPage();

    @PostMapping("/registration")
    String registerUser(User user, BindingResult bindingResult, Model model);

    @GetMapping("/activate/{code}")
    String activateEmail(Model model, @PathVariable("code") String code);
}
