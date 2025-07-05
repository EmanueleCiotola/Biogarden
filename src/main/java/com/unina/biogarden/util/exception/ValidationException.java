package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessages;

public class ValidationException extends Exception {
    public ValidationException(ErrorMessages message) {
        super(message.toString());
    }
}