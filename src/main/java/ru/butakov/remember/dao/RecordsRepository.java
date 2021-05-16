package ru.butakov.remember.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.butakov.remember.entity.Record;

public interface RecordsRepository extends JpaRepository<Record, Long> {
}
