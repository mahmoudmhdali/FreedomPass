package com.freedomPass.project.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidNameImpl implements ConstraintValidator<ValidName, String> {

    private String min;
    private String max;

    @Override
    public void initialize(ValidName constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        if (input != null) {
            if (input.contains("\\") || input.contains("/") || input.contains(":")
                    || input.contains("*") || input.contains("?") || input.contains("\"")
                    || input.contains("<") || input.contains(">") || input.contains("|")
                    || input.contains(".")) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate("validName.cantContains")
                        .addConstraintViolation();
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
