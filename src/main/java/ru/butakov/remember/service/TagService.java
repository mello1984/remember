package ru.butakov.remember.service;

import ru.butakov.remember.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag findByTagOrCreateNew(String tag);

    List<Tag> findAllByOrderByTagAsc();

    Optional<Tag> findById(int id);

    void delete(Tag tag);
}
