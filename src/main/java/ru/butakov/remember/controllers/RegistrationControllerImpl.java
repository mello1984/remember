package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class RegistrationControllerImpl implements RegistrationController {
    UserService userService;

    @Override
    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @Override
    @PostMapping("/registration")
    public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            model.addAttribute("passwordConfirmError", "Passwords not confirms");
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errorsMap);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User with this name already exists.");
            return "registration";
        }

        return "redirect:/login";
    }

    @Override
    @GetMapping("/activate/{code}")
    public String activateEmail(Model model, @PathVariable("code") String code) {
        boolean activated = userService.activateEmail(code);
        if (activated) {
            model.addAttribute("message", "Email activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}
