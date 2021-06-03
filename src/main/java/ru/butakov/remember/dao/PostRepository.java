package ru.butakov.remember.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.butakov.remember.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
