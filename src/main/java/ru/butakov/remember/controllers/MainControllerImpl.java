package ru.butakov.remember.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainControllerImpl implements MainController {

    @GetMapping("/")
    @Override
    public String hello(@RequestParam(required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
}
