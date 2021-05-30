package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
import ru.butakov.remember.exceptions.NotFoundException;
import ru.butakov.remember.exceptions.SecurityException;
import ru.butakov.remember.service.PostService;
import ru.butakov.remember.service.UserService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PostControllerImpl implements PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Value("${upload.path}")
    String uploadPath;

    @Override
    @GetMapping("/posts")
    public String index(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "/posts/index";
    }

    @Override
    @GetMapping("/posts/{id}")
    public String edit(@AuthenticationPrincipal User user, @PathVariable("id") long id, Model model) {
        Post postFromDb = getPostFromRepoAndCheckAuthor(user, id);
        model.addAttribute("post", postFromDb);
        return "/posts/edit";
    }

    @Override
    @GetMapping("/user-posts/{id}")
    public String userPosts(@PathVariable("id") int user, Model model) {
        User userFromDb = userService.findById(user).orElseThrow(NotFoundException::new);
        model.addAttribute("posts", userFromDb.getPosts());
        return "/posts/user-posts";
    }

    @Override
    @PostMapping("/posts")
    public String addPost(@AuthenticationPrincipal User user,
                          @Valid Post post,
                          BindingResult bindingResult,
                          Model model,
                          @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrorsMap(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("post", post);
            model.addAttribute("posts", postService.findAll());
            return "/posts/index";
        }

        post.setAuthor(user);
        if (file != null && !file.isEmpty()) updateFile(post, file);
        postService.save(post);
        return "redirect:/posts";
    }

    @Override
    @PatchMapping("/posts/{id}")
    public String updatePost(@AuthenticationPrincipal User user,
                             @Valid Post post,
                             BindingResult bindingResult,
                             @PathVariable("id") long id,
                             Model model,
                             @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            return String.format("redirect:/posts/%d", post.getId());
        }

        Post postFromDb = getPostFromRepoAndCheckAuthor(user, id);

        postFromDb.setText(post.getText());
        postFromDb.setTag(post.getTag());
        if (file != null && !file.isEmpty()) updateFile(postFromDb, file);
        postService.save(postFromDb);
        return "redirect:/posts";
    }

    @Override
    @DeleteMapping("/posts/{id}")
    public String deletePost(@AuthenticationPrincipal User user,
                             @PathVariable("id") long id) {
        Post postFromDb = getPostFromRepoAndCheckAuthor(user, id);
        postService.delete(postFromDb);
        return "redirect:/posts";
    }

    private Post getPostFromRepoAndCheckAuthor(User authenticatedUser, long id) {
        Post postFromDb = postService.findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("Post with id={0} not found in database", id)));
        if (!authenticatedUser.equals(postFromDb.getAuthor()))
            throw new SecurityException(MessageFormat.format("Authenticated user {0} not author of the post {1}", authenticatedUser, postFromDb));
        return postFromDb;
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
}
