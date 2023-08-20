package com.example.javarestcodingexercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TargetAccountIsSourceException extends Exception {
    public TargetAccountIsSourceException(long id) {
        super("Account id " + id + " is both source and target of transaction");
    }
}
