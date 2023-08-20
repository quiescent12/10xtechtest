package com.example.javarestcodingexercise;

import com.example.javarestcodingexercise.exception.AccountNotFoundException;
import com.example.javarestcodingexercise.database.AccountsRepository;
import com.example.javarestcodingexercise.database.AccountsService;
import com.example.javarestcodingexercise.model.Account;
import com.example.javarestcodingexercise.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountsServiceTests {

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountsService accountsService;

    @Test
    void getAccount_successfully() throws AccountNotFoundException {
        long accountId = 1234;
        Account expectedAccount = new Account(20.0, "GBP");

        Optional<Account> optionalAccount = Optional.of(expectedAccount);

        when(accountsRepository.findById(accountId)).thenReturn(optionalAccount);

        Account actualAccount = accountsService.getAccount(accountId);

        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void getAccount_accountNotFound() {
        long accountId = 1234;

        when(accountsRepository.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountsService.getAccount(accountId));
    }

    @Test
    void createAccount_successfully() {
        Account expectedAccount = new Account(20.0, "GBP");
        when(accountsRepository.save(any())).thenReturn(expectedAccount);

        Account actualAccount = accountsService.createAccount(expectedAccount.getBalance(), expectedAccount.getCurrency());

        verify(accountsRepository, times(1)).save(any());
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void transferMoney_successfully() {
        Account source = new Account(20.0, "GBP");
        source.setId(1234);
        Account target = new Account(20.0, "GBP");
        source.setId(2345);
        double transferAmount = 10.0;

        Transaction actualTransaction = accountsService.transferMoney(source, target, transferAmount);

        verify(accountsRepository, times(1)).updateAccountBalance(source.getId(), source.getBalance() - transferAmount);
        verify(accountsRepository, times(1)).updateAccountBalance(target.getId(), target.getBalance() + transferAmount);
        assertEquals(actualTransaction.sourceAccountId(), source.getId());
        assertEquals(actualTransaction.targetAccountId(), target.getId());
        assertEquals(actualTransaction.amount(), transferAmount);
    }
}
