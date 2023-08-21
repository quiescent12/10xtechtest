package com.example.javarestcodingexercise;

import com.example.javarestcodingexercise.controller.TransactionsController;
import com.example.javarestcodingexercise.service.AccountsService;
import com.example.javarestcodingexercise.exception.AccountNotFoundException;
import com.example.javarestcodingexercise.exception.InsufficientFundsException;
import com.example.javarestcodingexercise.exception.TargetAccountIsSourceException;
import com.example.javarestcodingexercise.model.Account;
import com.example.javarestcodingexercise.model.CreateAccountBody;
import com.example.javarestcodingexercise.model.Transaction;
import com.example.javarestcodingexercise.model.TransferRequestBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionsControllerTests {

	@Mock
	private AccountsService accountsService;

	@InjectMocks
	private TransactionsController transactionsController;


	@Test
	void transfer_makeTransactionSuccessfully() throws Exception {
		long sourceId = 1234;
		long targetId = 2345;
		TransferRequestBody transferRequestBody = new TransferRequestBody(sourceId, targetId, 10.0);
		Account source = new Account(20.0, "GBP");
		source.setId(sourceId);
		Account target = new Account(20.0, "GBP");
		target.setId(targetId);
		Transaction expectedTransaction = new Transaction(UUID.randomUUID().toString(), sourceId, targetId, transferRequestBody.amount(), "GBP");

		when(accountsService.getAccount(sourceId)).thenReturn(source);
		when(accountsService.getAccount(targetId)).thenReturn(target);
		when(accountsService.transferMoney(source, target, transferRequestBody.amount())).thenReturn(expectedTransaction);

		Transaction actualTransaction = transactionsController.transfer(transferRequestBody).getBody();

        assert actualTransaction != null;
        assertEquals(expectedTransaction, actualTransaction);
	}

	@Test
	void transfer_sourceAccountHasInsufficientBalanceForTransfer() throws Exception {
		long sourceId = 1234;
		long targetId = 2345;
		TransferRequestBody transferRequestBody = new TransferRequestBody(sourceId, targetId, 20.01);
		Account source = new Account(20.0, "GBP");
		source.setId(sourceId);
		Account target = new Account(20.0, "GBP");
		target.setId(targetId);

		when(accountsService.getAccount(sourceId)).thenReturn(source);
		when(accountsService.getAccount(targetId)).thenReturn(target);

		assertThrows(InsufficientFundsException.class, () -> transactionsController.transfer(transferRequestBody));
		verify(accountsService, never()).transferMoney(any(), any(), anyDouble());
	}

	@Test
	void transfer_targetAccountIsSourceAccount() {
		long sourceId = 1234;
		TransferRequestBody transferRequestBody = new TransferRequestBody(sourceId, sourceId, 10.0);

		assertThrows(TargetAccountIsSourceException.class, () -> transactionsController.transfer(transferRequestBody));
		verify(accountsService, never()).transferMoney(any(), any(), anyDouble());
	}

	@Test
	void transfer_sourceAccountDoesNotExist() throws AccountNotFoundException {
		long sourceId = 1234;
		long targetId = 2345;
		TransferRequestBody transferRequestBody = new TransferRequestBody(sourceId, targetId, 10.0);

		when(accountsService.getAccount(sourceId)).thenThrow(AccountNotFoundException.class);


		assertThrows(AccountNotFoundException.class, () -> transactionsController.transfer(transferRequestBody));
		verify(accountsService, never()).transferMoney(any(), any(), anyDouble());
	}

	@Test
	void transfer_targetAccountDoesNotExist() throws AccountNotFoundException {
		long sourceId = 1234;
		long targetId = 2345;
		TransferRequestBody transferRequestBody = new TransferRequestBody(sourceId, targetId, 10.0);
		Account source = new Account(20.0, "GBP");
		source.setId(sourceId);

		when(accountsService.getAccount(sourceId)).thenReturn(source);
		when(accountsService.getAccount(targetId)).thenThrow(AccountNotFoundException.class);


		assertThrows(AccountNotFoundException.class, () -> transactionsController.transfer(transferRequestBody));
		verify(accountsService, never()).transferMoney(any(), any(), anyDouble());
	}

	@Test
	void createAccount_successful() {
		CreateAccountBody createAccountBody = new CreateAccountBody(20.0, "GBP");
		Account expectedAccount = new Account(createAccountBody.balance(), createAccountBody.currency());

		when(accountsService.createAccount(createAccountBody.balance(), createAccountBody.currency())).thenReturn(expectedAccount);

		Account actualAccount = transactionsController.createAccount(createAccountBody).getBody();

		assertEquals(expectedAccount, actualAccount);
	}
}
