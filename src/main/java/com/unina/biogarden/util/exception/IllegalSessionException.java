package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessage;

public class IllegalSessionException extends Exception {
    public IllegalSessionException(ErrorMessage message) {
        super(message.toString());
    }
}
