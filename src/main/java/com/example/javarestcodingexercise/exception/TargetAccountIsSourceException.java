package com.example.javarestcodingexercise.exception;

public class TargetAccountIsSourceException extends Exception {
    public TargetAccountIsSourceException(long id) {
        super("Account id " + id + " is both source and target of transaction");
    }
}
