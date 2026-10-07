package com.carrental.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.carrental.model.RentalRequest;

public class RentalDateValidator
        implements ConstraintValidator<ValidRentalDates, RentalRequest> {

    @Override
    public boolean isValid(RentalRequest request, ConstraintValidatorContext context) {

        if (request == null) {
            return true;
        }

        if (request.getStartDate() == null || request.getEndDate() == null) {
            return true;
        }

        return request.getEndDate().isAfter(request.getStartDate());
    }
}