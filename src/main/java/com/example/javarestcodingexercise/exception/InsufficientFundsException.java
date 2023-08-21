package com.example.javarestcodingexercise.exception;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(long id) {
        super("Account id " + id + " does not have enough funds for this transfer");
    }
}
