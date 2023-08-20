package com.example.javarestcodingexercise.database;

import com.example.javarestcodingexercise.exception.AccountNotFoundException;
import com.example.javarestcodingexercise.model.Account;
import com.example.javarestcodingexercise.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountsService {
    @Autowired
    private AccountsRepository accountsRepository;

    public Account getAccount(long id) throws AccountNotFoundException {
        Optional<Account> account = accountsRepository.findById(id);

        if (account.isEmpty()) {
            throw new AccountNotFoundException(id);
        }
        return account.get();
    }

    public Account createAccount(double balance, String currency) {
        return accountsRepository.save(new Account(balance, currency, LocalDateTime.now()));
    }

    private void updateBalance(long id, double newBalance) {
        accountsRepository.updateAccountBalance(id, newBalance);
    }

    public Transaction transferMoney(Account source, Account target, double amount) {
        double sourceNewBalance = source.getBalance() - amount;
        double targetNewBalance = target.getBalance() + amount;
        updateBalance(source.getId(), sourceNewBalance);
        updateBalance(target.getId(), targetNewBalance);
        return new Transaction(
                UUID.randomUUID().toString(),
                source.getId(),
                target.getId(),
                amount,
                source.getCurrency()
        );
    }
}
