package com.example.javarestcodingexercise.model;

public record TransferRequestBody(long sourceAccountId, long targetAccountId, double amount) {
}
