package ru.butakov.remember.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.butakov.remember.entity.User;

import java.util.Map;

public interface UserController {
    @GetMapping
    String getUsers(Model model);

    @GetMapping("/{user}")
    String userEdit(@PathVariable User user, Model model);

    @PostMapping
    String userSave(@RequestParam("userId") User user,
                    @RequestParam("username") String username,
                    @RequestParam(value = "active", required = false) boolean active,
                    @RequestParam Map<String, String> form);
}
