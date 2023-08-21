package com.example.javarestcodingexercise;

import com.example.javarestcodingexercise.database.AccountsRepository;
import com.example.javarestcodingexercise.database.TransactionsRepository;
import com.example.javarestcodingexercise.model.Account;
import com.example.javarestcodingexercise.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TransactionsRepositoryTests {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void saveToDatabase_successful() {
        Transaction transaction = new Transaction(1234, 2345, 20.0,"GBP");

        Transaction transactionExpected = transactionsRepository.save(transaction);

        Transaction transactionActual = testEntityManager.find(Transaction.class, transactionExpected.getId());

        assertEquals(transaction, transactionActual);
    }
}
