package ru.butakov.remember.service;

import ru.butakov.remember.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(long id);

    void delete(Post post);
}
