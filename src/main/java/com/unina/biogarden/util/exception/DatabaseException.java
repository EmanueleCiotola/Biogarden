package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessage;

public class DatabaseException extends Exception {
    public DatabaseException(ErrorMessage message) {
        super(message.toString());
    }
    public DatabaseException(String string) {
        super(string);
    }
}
