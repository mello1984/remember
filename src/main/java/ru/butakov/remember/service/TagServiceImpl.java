package ru.butakov.remember.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.butakov.remember.dao.TagRepository;
import ru.butakov.remember.entity.Tag;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public Tag findByTagOrCreateNew(String tag) {
        return tagRepository.findByTag(tag).orElseGet(() -> tagRepository.save(new Tag(tag)));
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(int id) {
        return tagRepository.findById(id);
    }

    @Override
    public void delete(Tag tag) {
        tagRepository.delete(tag);
    }
}
