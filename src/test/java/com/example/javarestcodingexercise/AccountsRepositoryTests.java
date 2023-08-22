package com.example.javarestcodingexercise;

import com.example.javarestcodingexercise.database.AccountsRepository;
import com.example.javarestcodingexercise.model.Account;
import com.example.javarestcodingexercise.model.Currency;
import com.example.javarestcodingexercise.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountsRepositoryTests {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void updateAccountBalance_successful() {
        Account account = testEntityManager.persist(new Account(20.0, Currency.GBP, LocalDateTime.now()));

        long accountId = account.getId();
        accountsRepository.updateAccountBalance(accountId, 30.0);

        Account accountActual = testEntityManager.find(Account.class, accountId);

        assertEquals(30.0, accountActual.getBalance());
    }

    @Test
    void saveToDatabase_successful() {
        Account account = new Account(20.0, Currency.GBP, LocalDateTime.now());

        Account accountExpected = accountsRepository.save(account);

        Account accountActual = testEntityManager.find(Account.class, accountExpected.getId());

        assertEquals(account, accountActual);
    }

    @Test
    void findById_successful() {
        Account account = testEntityManager.persist(new Account(20.0, Currency.GBP, LocalDateTime.now()));

        long accountId = account.getId();
        Account accountActual = accountsRepository.findById(accountId).orElseThrow();

        assertEquals(account, accountActual);
    }
}
