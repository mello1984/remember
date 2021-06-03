package ru.butakov.remember.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.butakov.remember.dao.PostRepository;
import ru.butakov.remember.entity.Post;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    PostRepository postRepository;

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
}
