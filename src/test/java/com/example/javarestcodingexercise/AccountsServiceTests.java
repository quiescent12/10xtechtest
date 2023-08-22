package com.example.javarestcodingexercise;

import com.example.javarestcodingexercise.database.TransactionsRepository;
import com.example.javarestcodingexercise.exception.AccountNotFoundException;
import com.example.javarestcodingexercise.database.AccountsRepository;
import com.example.javarestcodingexercise.model.Currency;
import com.example.javarestcodingexercise.service.AccountsService;
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
    private TransactionsRepository transactionsRepository;

    @Mock
    private AccountsRepository accountsRepository;

    @InjectMocks
    private AccountsService accountsService;

    @Test
    void getAccount_successfully() throws AccountNotFoundException {
        long accountId = 1234;
        Account expectedAccount = new Account(20.0, Currency.GBP);

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
        Account expectedAccount = new Account(20.0, Currency.GBP);
        when(accountsRepository.save(any())).thenReturn(expectedAccount);

        Account actualAccount = accountsService.createAccount(expectedAccount.getBalance(), expectedAccount.getCurrency());

        verify(accountsRepository, times(1)).save(any());
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void transferMoney_bothAccountsAreUpdatedSuccessfully() {
        Account source = new Account(20.0, Currency.GBP);
        source.setId(1234);
        Account target = new Account(20.0, Currency.GBP);
        target.setId(2345);
        double transferAmount = 10.0;

        accountsService.transferMoney(source, target, transferAmount);

        verify(accountsRepository, times(1)).updateAccountBalance(source.getId(), source.getBalance() - transferAmount);
        verify(accountsRepository, times(1)).updateAccountBalance(target.getId(), target.getBalance() + transferAmount);
    }

    @Test
    void transferMoney_transactionIsReturnedSuccessfully() {
        Account source = new Account(20.0, Currency.GBP);
        source.setId(1234);
        Account target = new Account(20.0, Currency.GBP);
        target.setId(2345);
        double transferAmount = 10.0;
        Transaction expectedTransaction = new Transaction(source.getId(), target.getId(), transferAmount, source.getCurrency());
        when(transactionsRepository.save(any())).thenReturn(expectedTransaction);

        Transaction actualTransaction = accountsService.transferMoney(source, target, transferAmount);

        verify(transactionsRepository, times(1)).save(any());
        assertEquals(expectedTransaction, actualTransaction);
    }
}
