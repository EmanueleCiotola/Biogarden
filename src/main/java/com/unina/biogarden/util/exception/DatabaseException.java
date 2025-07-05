package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessages;

public class DatabaseException extends Exception {
    public DatabaseException(ErrorMessages message) {
        super(message.toString());
    }
}
