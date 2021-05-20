package ru.butakov.remember.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.butakov.remember.dao.RecordsRepository;
import ru.butakov.remember.entity.Record;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RecordsServiceImpl implements RecordsService {
    RecordsRepository recordsRepository;

    @Override
    public Record save(Record record) {
        return recordsRepository.save(record);
    }

    @Override
    public List<Record> findAll(){
        return recordsRepository.findAll();
    }

    @Override
    public Optional<Record> findById(long id){
        return recordsRepository.findById(id);
    }

    @Override
    public void delete(Record record){
        recordsRepository.delete(record);
    }
}
