package com.example.javarestcodingexercise.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "accounts")
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private double balance;

    @Column
    private String currency;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Account() {}

    public Account(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    public Account(double balance, String currency, LocalDateTime createdAt) {
        this.balance = balance;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("Account[id=%d, balance='%s', currency='%s', createdAt='%s']", id, balance, currency, createdAt);
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
