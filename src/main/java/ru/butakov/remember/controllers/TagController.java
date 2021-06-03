package ru.butakov.remember.controllers;

import org.springframework.ui.Model;

public interface TagController {
    String index(Model model);

    String delete(int id);
}
