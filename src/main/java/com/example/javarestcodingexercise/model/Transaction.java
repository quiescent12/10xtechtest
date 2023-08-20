package com.example.javarestcodingexercise.model;

public record Transaction(String id, long sourceAccountId, long targetAccountId, double amount, String currency) {
}
