package ru.butakov.remember.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.butakov.remember.entity.User;

import java.io.IOException;

public interface RecordsController {
    @GetMapping
    String records(Model model);

    @PostMapping
    String addRecord(@AuthenticationPrincipal User user,
                     @RequestParam String text,
                     @RequestParam String tag,
                     @RequestParam("file") MultipartFile file) throws IOException;

    @GetMapping("/{id}")
    String records(@PathVariable("id") long id, Model model);

    @PostMapping("/{id}/edit")
    String editRecord(@PathVariable("id") long id,
                      @RequestParam String text,
                      @RequestParam String tag,
                      @RequestParam("file") MultipartFile file) throws IOException;

    @PostMapping("/{id}/delete")
    String deleteRecord(@PathVariable("id") long id);
}
