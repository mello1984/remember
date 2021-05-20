package ru.butakov.remember.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface RegistrationController {
    @GetMapping("/registration")
    String registration();

    @PostMapping("/registration")
    String registration(@RequestParam String username, @RequestParam String password, Model model);
}
