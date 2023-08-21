package com.example.javarestcodingexercise.exception;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(long id) {
        super("Account id " + id + " not found");
    }
}
