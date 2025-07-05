package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessages;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(ErrorMessages message) {
        super(message.toString());
    }
}