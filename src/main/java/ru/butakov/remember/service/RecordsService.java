package ru.butakov.remember.service;

import ru.butakov.remember.entity.Record;

import java.util.List;
import java.util.Optional;

public interface RecordsService {
    Record save(Record record);

    List<Record> findAll();

    Optional<Record> findById(long id);

    void delete(Record record);
}
