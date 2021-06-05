package ru.butakov.remember.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.butakov.remember.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByTag(String tag);

    List<Tag> findAllByOrderByTagAsc();
}
