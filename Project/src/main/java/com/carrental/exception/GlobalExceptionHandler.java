package com.carrental.exception;
import org.springframework.validation.ObjectError;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCarNotFound(CarNotFoundException ex) {
        return ex.getMessage();
    }
    
    @ExceptionHandler(RentalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRentalNotFound(RentalNotFoundException ex) {
        return ex.getMessage();
    }
    
    @ExceptionHandler(CarNotAvailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleRentalNotFound(CarNotAvailableException ex) {
        return ex.getMessage();
    }
    
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCustomerNotFound(CustomerNotFoundException ex) {
        return ex.getMessage();
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        for (ObjectError error : ex.getGlobalErrors()) {
            errors.put("date", error.getDefaultMessage());
        }

        return errors;
    }
    
}