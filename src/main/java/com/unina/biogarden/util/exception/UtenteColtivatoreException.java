package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessage;

public class UtenteColtivatoreException extends Exception {
    public UtenteColtivatoreException(ErrorMessage message) {
        super(message.toString());
    }
}
