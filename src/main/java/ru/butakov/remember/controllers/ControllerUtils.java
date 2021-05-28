package ru.butakov.remember.controllers;

import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrorsMap(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                fieldError -> {
                    if (fieldError.getDefaultMessage() == null)
                        throw new RuntimeException(String.format("BindingResult has not message field on field '%s'", fieldError.getField()));
                    return fieldError.getDefaultMessage();
                }
        ));
    }
}
