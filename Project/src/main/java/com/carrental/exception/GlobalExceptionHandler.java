package com.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}