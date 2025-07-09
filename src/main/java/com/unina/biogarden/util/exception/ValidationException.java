package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessage;

public class ValidationException extends Exception {
    public ValidationException(ErrorMessage message) {
        super(message.toString());
    }
}