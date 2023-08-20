package com.example.javarestcodingexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(long id) {
        super("Account id " + id + " not found");
    }
}
