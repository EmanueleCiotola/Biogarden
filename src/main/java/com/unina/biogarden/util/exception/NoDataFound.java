package com.unina.biogarden.util.exception;

import com.unina.biogarden.util.ErrorMessage;

public class NoDataFound extends Exception {
    public NoDataFound(ErrorMessage message) {
        super(message.toString());
    }
}
