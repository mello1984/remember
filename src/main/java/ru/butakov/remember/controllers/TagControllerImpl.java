package ru.butakov.remember.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.butakov.remember.entity.Tag;
import ru.butakov.remember.service.TagService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class TagControllerImpl implements TagController {
    @Autowired
    TagService tagService;

    @Override
    @GetMapping("/tags")
    public String index(Model model) {
        List<Tag> tagList = tagService.findAll();
        tagList.sort(Comparator.comparing(Tag::getTag));
        model.addAttribute("tags", tagList);

        return "/tags/index";
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/tags")
    public String delete(@RequestParam("id") int id) {
        Optional<Tag> tag = tagService.findById(id);
        tag.ifPresent(value -> tagService.delete(value));
        return "redirect:/tags";
    }
}
