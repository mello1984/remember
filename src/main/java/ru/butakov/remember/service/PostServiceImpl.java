package ru.butakov.remember.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.butakov.remember.dao.PostRepository;
import ru.butakov.remember.entity.Post;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.entity.dto.PostDto;
import ru.butakov.remember.exceptions.NotFoundException;
import ru.butakov.remember.exceptions.SecurityException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    PostRepository postRepository;
    TagService tagService;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(long id) {
        return postRepository.findById(id);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> postDtoList(String tag, User currentUser) {
        List<Post> posts = tag == null || tag.isEmpty() ?
                postRepository.findAll() :
                tagService.findByTagOrCreateNew(tag).getPosts();

        return posts.stream()
                .map(p -> new PostDto(p, currentUser))
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostOrThrowException(long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(MessageFormat.format("Post with id={0} not found in database", id)));
    }

    @Override
    public Post getPostAndCheckAuthor(User authenticatedUser, long id) {
        Post postFromDb = getPostOrThrowException(id);
        if (!authenticatedUser.equals(postFromDb.getAuthor()))
            throw new SecurityException(MessageFormat.format("Authenticated user {0} not author of the post {1}", authenticatedUser, postFromDb));
        return postFromDb;
    }

    @Override
    public void toggleLike(long postId, User user) {
        Post postFromDb = getPostOrThrowException(postId);
        if (postFromDb.getLikes().contains(user)) postFromDb.getLikes().remove(user);
        else postFromDb.getLikes().add(user);
    }
}
