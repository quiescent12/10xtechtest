package com.example.javarestcodingexercise;

import com.example.javarestcodingexercise.database.AccountsRepository;
import com.example.javarestcodingexercise.model.Account;
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
        Account account = testEntityManager.persist(new Account(20.0, "GBP", LocalDateTime.now()));

        long accountId = account.getId();
        accountsRepository.updateAccountBalance(accountId, 30.0);

        Account accountActual = testEntityManager.find(Account.class, accountId);

        assertEquals(30.0, accountActual.getBalance());
    }
}
