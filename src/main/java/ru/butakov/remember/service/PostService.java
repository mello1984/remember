package ru.butakov.remember.service;

import ru.butakov.remember.entity.Post;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.entity.dto.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post save(Post post);

    List<Post> findAll();

    Optional<Post> findById(long id);

    void delete(Post post);

    List<PostDto> postDtoList(String tag, User currentUser);

    Post getPostOrThrowException(long id);

    Post getPostAndCheckAuthor(User authenticatedUser, long id);

    void toggleLike(long postId, User user);
}
