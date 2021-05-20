package ru.butakov.remember.controllers;

import org.springframework.ui.Model;

public interface MainController {
    String hello(String name, Model model);
}
