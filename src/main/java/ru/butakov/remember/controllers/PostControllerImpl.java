package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.butakov.remember.entity.Post;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.exceptions.NoSuchUserException;
import ru.butakov.remember.exceptions.NotFoundException;
import ru.butakov.remember.service.PostService;
import ru.butakov.remember.service.UserService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Controller
@RequestMapping(value = "/posts")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostControllerImpl implements PostController {
    @Autowired
    PostService postService;
    @Value("${upload.path}")
    String uploadPath;
    @Autowired
    UserService userService;

    @Override
    @GetMapping
    public String post(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "/posts/posts";
    }

    @Override
    @GetMapping("/{id}")
    public String post(@PathVariable("id") long id, Model model) {
        Optional<Post> recordFromDb = postService.findById(id);
        if (recordFromDb.isEmpty()) throw new NotFoundException();
        model.addAttribute("post", recordFromDb.get());
        return "/posts/post";
    }

    @Override
    @PostMapping
    public String add(@AuthenticationPrincipal User user,
                      @Valid Post post,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("post", post);

            List<Post> posts = postService.findAll();
            model.addAttribute("posts", posts);
            return "/posts/posts";
        }

        post.setAuthor(user);

        if (file != null && !file.isEmpty()) updateFile(post, file);
        postService.save(post);
        return "redirect:/posts";
    }


    @Override
    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id,
                       @RequestParam String text,
                       @RequestParam String tag,
                       @RequestParam("file") MultipartFile file) throws IOException {
        Optional<Post> recordFromDb = postService.findById(id);
        if (recordFromDb.isEmpty()) throw new NotFoundException();
        Post post = recordFromDb.get();
        post.setText(text);
        post.setTag(tag);

        if (file != null && !file.isEmpty()) updateFile(post, file);
        postService.save(post);
        return "redirect:/posts";
    }

    private void updateFile(Post post, MultipartFile newFile) throws IOException {
        if (newFile != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            if (post.getFilename() != null) {
                File oldFile = new File(uploadPath + "/" + post.getFilename());
                if (oldFile.exists()) oldFile.delete();
            }

            String newFilename = UUID.randomUUID().toString() + "." + newFile.getOriginalFilename();
            post.setFilename(newFilename);
            newFile.transferTo(new File(uploadPath + "/" + newFilename));
        }
    }

    @Override
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        Optional<Post> recordFromDb = postService.findById(id);
        if (recordFromDb.isEmpty()) throw new NotFoundException();
        postService.delete(recordFromDb.get());
        return "redirect:/posts";
    }

    @Override
    @GetMapping("/user-posts")
    public String userPosts(@AuthenticationPrincipal User user, Model model) {
        User userFromDb = userService.findById(user.getId()).orElseThrow(NoSuchUserException::new);
        model.addAttribute("posts", userFromDb.getPosts());
        return "/posts/user-posts";
    }

}
