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
    public Map<String,String> handleCarNotFound(CarNotFoundException ex) {
        return Map.of("message",ex.getMessage());
    }
    @ExceptionHandler(RentalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleRentalNotFound(RentalNotFoundException ex) {
    	return Map.of("message",ex.getMessage());
    }
    @ExceptionHandler(CarNotAvailableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String,String> handleRentalNotFound(CarNotAvailableException ex) {
    	return Map.of("message",ex.getMessage());
    }
    
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleCustomerNotFound(CustomerNotFoundException ex) {
    	return Map.of("message",ex.getMessage());
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
    @ExceptionHandler(MaintenanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleMaintenanceNotFound(MaintenanceNotFoundException ex) {
    	return Map.of("message",ex.getMessage());
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleIllegalArgument(IllegalArgumentException ex) {
    	return Map.of("message",ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> handleUnexpectedException(Exception ex) {
    	return Map.of("message","An unexpected error occured.Please try again later");
    	}
}