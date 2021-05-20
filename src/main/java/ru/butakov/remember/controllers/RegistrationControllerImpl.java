package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.butakov.remember.entity.Role;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.service.UserService;

import java.util.Collections;
import java.util.Optional;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationControllerImpl implements RegistrationController {
    UserService userService;
    PasswordEncoder passwordEncoder;

    public RegistrationControllerImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Override
    @PostMapping("/registration")
    public String registration(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> userFromDb = userService.findByUsername(username);

        if (userFromDb.isPresent()) {
            model.addAttribute("userExistsMessage", "User with this name already exists.");
            return "registration";
        }

        User user = new User(username, passwordEncoder.encode(password), Collections.singleton(Role.USER));
        userService.save(user);
        return "redirect:/login";
    }
}
