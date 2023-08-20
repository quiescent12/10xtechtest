package com.example.javarestcodingexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(long id) {
        super("Account id " + id + " does not have enough funds for this transfer");
    }
}
