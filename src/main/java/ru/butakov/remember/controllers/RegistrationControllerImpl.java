package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.service.UserService;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationControllerImpl implements RegistrationController {
    @Autowired
    UserService userService;

    @Override
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @Override
    @PostMapping("/registration")
    public String registration(User user, Model model) {

        if (!userService.addUser(user)) {
            model.addAttribute("existsMessage", "User with this name already exists.");
            return "registration";
        }
        return "redirect:/login";
    }

    @Override
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable("code") String code) {
        boolean activated = userService.activateEmail(code);
        if (activated) {
            model.addAttribute("message", "Email activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}
