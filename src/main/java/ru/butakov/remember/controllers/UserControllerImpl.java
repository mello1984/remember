package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.butakov.remember.entity.Role;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class UserControllerImpl implements UserController {
    UserService userService;

    @Override
    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/users/user_list";
    }

    @Override
    @GetMapping("/{user}")
    public String userEdit(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", List.of(Role.values()));
//        model.addAttribute("roles", Role.values()); //???
        return "/users/user_edit";
    }

    @Override
    @PostMapping
    public String userSave(@RequestParam("userId") User user,
                           @RequestParam("username") String username,
                           @RequestParam(value = "active", required = false) boolean active,
                           @RequestParam Map<String, String> form) {
        user.setUsername(username);
        user.setActive(active);
        user.getRoles().clear();

        Set<String> roles = Arrays.stream(Role.values()).map(Enum::name).collect(Collectors.toSet());
        form.keySet().stream()
                .filter(roles::contains)
                .forEach(r -> user.getRoles().add(Role.valueOf(r)));

        userService.save(user);
        return "redirect:/users";
    }

}
