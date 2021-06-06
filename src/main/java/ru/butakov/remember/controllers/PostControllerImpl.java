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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import ru.butakov.remember.entity.Post;
import ru.butakov.remember.entity.Tag;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.exceptions.NotFoundException;
import ru.butakov.remember.service.PostService;
import ru.butakov.remember.service.TagService;
import ru.butakov.remember.service.UserService;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class PostControllerImpl implements PostController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;
    @Value("${upload.path}")
    String uploadPath;

    @Override
    @GetMapping("/posts")
    public String filterByTag(@AuthenticationPrincipal User user,
                              @RequestParam(value = "tag", required = false) String tag,
                              Model model) {

        model.addAttribute("tag", tag == null ? "" : tag);
        model.addAttribute("tags", tagService.findAllByOrderByTagAsc());
        model.addAttribute("posts", postService.postDtoList(tag, user));
        return "/posts/index";
    }

    @Override
    @GetMapping("/posts/{id}")
    public String edit(@AuthenticationPrincipal User user, @PathVariable("id") long id, Model model) {
        Post postFromDb = postService.getPostAndCheckAuthor(user, id);
        String tags = postFromDb.getTags().stream().map(Tag::getTag).collect(Collectors.joining(";"));

        model.addAttribute("post", postFromDb);
        model.addAttribute("tags", tags);
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
            model.addAttribute("tag", "");
            model.addAttribute("tags", Collections.emptyList());
            return "/posts/index";
        }

        Set<Tag> tags = getTagSetFromTagString(post);
        post.getTags().addAll(tags);
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

        Post postFromDb = postService.getPostAndCheckAuthor(user, id);
        postFromDb.setText(post.getText());

        Set<Tag> tags = getTagSetFromTagString(post);
        postFromDb.getTags().clear();
        postFromDb.getTags().addAll(tags);
        postFromDb.setTag(post.getTag());

        if (file != null && !file.isEmpty()) updateFile(postFromDb, file);
        postService.save(postFromDb);
        return "redirect:/posts";
    }

    private Set<Tag> getTagSetFromTagString(@Valid Post post) {
        return Arrays.stream(post.getTag().toLowerCase().split(";"))
                .map(String::strip)
                .map(tagService::findByTagOrCreateNew)
                .collect(Collectors.toSet());
    }

    @Override
    @DeleteMapping("/posts/{id}")
    public String deletePost(@AuthenticationPrincipal User user,
                             @PathVariable("id") long id) {
        Post postFromDb = postService.getPostAndCheckAuthor(user, id);
        postService.delete(postFromDb);
        return "redirect:/posts";
    }

    @Override
    @GetMapping("/posts/{id}/like")
    public String likePost(@AuthenticationPrincipal User user,
                           @PathVariable("id") long id,
                           RedirectAttributes redirectAttributes,
                           @RequestHeader(required = false) String referer) {

        postService.toggleLike(id, user);

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components
                .getQueryParams()
                .forEach(redirectAttributes::addAttribute);

        return "redirect:" + components.getPath();
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
