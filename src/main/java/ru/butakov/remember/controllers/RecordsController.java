package ru.butakov.remember.controllers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.butakov.remember.entity.Record;
import ru.butakov.remember.entity.User;
import ru.butakov.remember.exceptions.NotFoundException;
import ru.butakov.remember.service.RecordsService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping(value = "/records")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RecordsController {
    @Autowired
    RecordsService recordsService;
    @Value("${upload.path}")
    String uploadPath;

    @GetMapping
    public String records(Model model) {
        List<Record> records = recordsService.findAll();
        model.addAttribute("records", records);
        return "/records/records";
    }

    @PostMapping
    public String addRecord(@AuthenticationPrincipal User user,
                            @RequestParam String text,
                            @RequestParam String tag,
                            @RequestParam("file") MultipartFile file) throws IOException {
        Record record = new Record(text, tag, user);
        if (file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String newFilename = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
            record.setFilename(newFilename);
            file.transferTo(new File(uploadPath + "/" + newFilename));
        }
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
