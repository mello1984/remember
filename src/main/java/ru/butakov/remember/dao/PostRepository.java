package ru.butakov.remember.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.butakov.remember.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTag(String tag);

    @Query("SELECT DISTINCT p.tag FROM Post p ORDER BY p.tag")
    List<String> findUniqueTags();
}
