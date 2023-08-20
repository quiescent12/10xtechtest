package com.example.javarestcodingexercise.controller;

import com.example.javarestcodingexercise.database.AccountsService;
import com.example.javarestcodingexercise.exception.InsufficientFundsException;
import com.example.javarestcodingexercise.exception.TargetAccountIsSourceException;
import com.example.javarestcodingexercise.model.Account;
import com.example.javarestcodingexercise.model.CreateAccountBody;
import com.example.javarestcodingexercise.model.Transaction;
import com.example.javarestcodingexercise.model.TransferRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {
    @Autowired
    private AccountsService accountsService;

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequestBody transferRequestBody) throws Exception {
        if (transferRequestBody.sourceAccountId() == transferRequestBody.targetAccountId()) {
            throw new TargetAccountIsSourceException(transferRequestBody.sourceAccountId());
        }

        Account source = accountsService.getAccount(transferRequestBody.sourceAccountId());
        Account target = accountsService.getAccount(transferRequestBody.targetAccountId());

        if (source.getBalance() < transferRequestBody.amount()) {
            throw new InsufficientFundsException(source.getId());
        }

        Transaction transaction = accountsService.transferMoney(source, target, transferRequestBody.amount());
        return  ResponseEntity.ok(transaction);
    }

    @PostMapping(path = "/createAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountBody createAccountBody) {
        String accountCurrency = "GBP";
        if(createAccountBody.currency() != null) {
            accountCurrency = createAccountBody.currency();
        }
        Account account = accountsService.createAccount(createAccountBody.balance(), accountCurrency);
        return ResponseEntity.ok(account);
    }
}
