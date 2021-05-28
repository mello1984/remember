package ru.butakov.remember.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControllerImpl implements MainController {

    @GetMapping("/")
    @Override
    public String main() {
        return "main";
    }
}
