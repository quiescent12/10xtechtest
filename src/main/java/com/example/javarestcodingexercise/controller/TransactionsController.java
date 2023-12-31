package com.example.javarestcodingexercise.controller;

import com.example.javarestcodingexercise.model.*;
import com.example.javarestcodingexercise.service.AccountsService;
import com.example.javarestcodingexercise.exception.InsufficientFundsException;
import com.example.javarestcodingexercise.exception.TargetAccountIsSourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountsService accountsService;

    @PostMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Transaction> transfer(@RequestBody TransferRequestBody transferRequestBody) throws Exception {
        if (transferRequestBody.sourceAccountId() == transferRequestBody.targetAccountId()) {
            throw new TargetAccountIsSourceException(transferRequestBody.sourceAccountId());
        }
        // TODO: Account checks should maybe be part of update balance transaction
        Account source = accountsService.getAccount(transferRequestBody.sourceAccountId());
        Account target = accountsService.getAccount(transferRequestBody.targetAccountId());

        if (source.getBalance() < transferRequestBody.amount()) {
            throw new InsufficientFundsException(source.getId());
        }

        Transaction transaction = accountsService.transferMoney(source, target, transferRequestBody.amount());
        logger.info("Transaction made: " + transaction);
        return  ResponseEntity.ok(transaction);
    }

    @PostMapping(path = "/createAccount", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountBody createAccountBody) {
        Currency accountCurrency = Currency.GBP;
        if(createAccountBody.currency() != null) {
            accountCurrency = createAccountBody.currency();
        }
        Account account = accountsService.createAccount(createAccountBody.balance(), accountCurrency);
        logger.info("Account created: " + account);
        return ResponseEntity.ok(account);
    }
}
