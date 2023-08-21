package com.example.javarestcodingexercise.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity(name = "transactions")
@Table(name = "transactions")
public class Transaction {
    @Id
    @UuidGenerator
    private String id;

    @Column(name = "source_account_id")
    private long sourceAccountId;

    @Column(name = "target_account_id")
    private long targetAccountId;

    @Column
    private double amount;

    @Column
    private String currency;

    protected Transaction() {}

    public Transaction(long sourceAccountId, long targetAccountId, double amount, String currency) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%s, sourceAccountId='%d', targetAccountId='%d', amount='%s', currency='%s'']", id, sourceAccountId, targetAccountId, amount, currency);
    }

    public String getId() {
        return id;
    }

    public long getSourceAccountId() {
        return sourceAccountId;
    }

    public long getTargetAccountId() {
        return targetAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSourceAccountId(long sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public void setTargetAccountId(long targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}