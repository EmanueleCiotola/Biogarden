package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessage;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(ErrorMessage message) {
        super(message.toString());
    }
}