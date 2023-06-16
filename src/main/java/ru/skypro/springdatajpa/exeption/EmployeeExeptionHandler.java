package ru.skypro.springdatajpa.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExeptionHandler {
    @ExceptionHandler(EmployeeNotFoundExeption.class)
    public ResponseEntity<?> handleNotFound(EmployeeNotFoundExeption e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Сотрудник с id = %d не найден".formatted(e.getId()));
    }

    @ExceptionHandler(EmployeeNotValidExeption.class)
    public ResponseEntity<?> handleBadRequest(EmployeeNotValidExeption e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Некорректные данные сотрудника %s" .formatted(e.getEmployee()));
    }
}