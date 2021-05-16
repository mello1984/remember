package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.butakov.remember.entity.Record;
import ru.butakov.remember.exceptions.NotFoundException;
import ru.butakov.remember.service.RecordsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/records")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecordsController {
    RecordsService recordsService;

    @GetMapping
    public String records(Model model) {
        List<Record> records = recordsService.findAll();
        model.addAttribute("records", records);
        return "/records/records";
    }

    @PostMapping
    public String addRecord(@RequestParam String text, @RequestParam String tag) {
        Record record = new Record(text, tag);
        recordsService.save(record);
        return "redirect:/records";
    }

    @GetMapping("/{id}")
    public String records(@PathVariable("id") long id, Model model) {
        Optional<Record> recordFromDb = recordsService.findById(id);
        if (recordFromDb.isEmpty()) throw new NotFoundException();
        model.addAttribute("record", recordFromDb.get());
        return "/records/record";
    }

    @PostMapping("/{id}/edit")
    public String editRecord(@PathVariable("id") long id, @RequestParam String text, @RequestParam String tag) {
        Optional<Record> recordFromDb = recordsService.findById(id);
        if (recordFromDb.isEmpty()) throw new NotFoundException();
        Record record = recordFromDb.get();
        record.setDate(LocalDate.now());
        record.setText(text);
        record.setTag(tag);
        recordsService.save(record);
        return "redirect:/records";
    }

    @PostMapping("/{id}/delete")
    public String deleteRecord(@PathVariable("id") long id) {
        Optional<Record> recordFromDb = recordsService.findById(id);
        if (recordFromDb.isEmpty()) throw new NotFoundException();
        recordsService.delete(recordFromDb.get());
        return "redirect:/records";
    }

}
