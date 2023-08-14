package com.bank.accountapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bank.accountapi.services.AccountFinderService;
import com.bank.accountapi.services.TransactionFinderService;
import com.bank.accountapi.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.accountapi.models.Account;
import com.bank.accountapi.models.Transaction;
import com.bank.accountapi.services.AccountService;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AccountapiApplicationTests {

	private final AccountService accountService;
	private final AccountFinderService accountFinderService;
	private final TransactionService transactionService;
	private final TransactionFinderService transactionFinderService;
	
	@Test
	void when_createAccount_given_validInputs_then_accountIsCreated() {
		Account expectedAccount = new Account(3847, 194857, "Customer", new BigDecimal(0));
		Account account = accountService.createAccount("Customer", 3847, 194857, new BigDecimal(0));
		
		assertEquals(expectedAccount, account);
	}
	
	@Test
	void when_createAccount_given_negativeStartBalance_then_exceptionThrown() {
		assertThrows(IllegalArgumentException.class, () -> {			
			accountService.createAccount("Customer", 3847, 194857, new BigDecimal(-50));
		});
	}
	
	@Test
	void when_deposit100_given_balance0_then_balance100() {
		Account account = accountService.createAccount("Customer", 3847, 194857, new BigDecimal(0));

		transactionService.createTransaction(account.getId(), new BigDecimal(100));
		
		Account resultAccount = accountFinderService.getAccount(account.getId());

        assertEquals(0, new BigDecimal(100).compareTo(resultAccount.getBalance()));
	}
	
	@Test
	void when_withdraw50_given_balance100_then_balance50() {
		Account account = accountService.createAccount("Customer", 3847, 194857, new BigDecimal(100));

		transactionService.createTransaction(account.getId(), new BigDecimal(-50));
		
		Account resultAccount = accountFinderService.getAccount(account.getId());

        assertEquals(0, new BigDecimal(50).compareTo(resultAccount.getBalance()));
	}
	
	@Test
	void when_withdraw50_given_balance0_then_exceptionThrown() {
		Account account = accountService.createAccount("Customer", 3847, 194857, new BigDecimal(0));
		
		assertThrows(IllegalStateException.class, () -> transactionService.createTransaction(account.getId(), new BigDecimal(-50)));
	}
	
	@Test
	void when_getBalance_given_balance250_then_250Returned() {
		Account account = accountService.createAccount("Customer", 3847, 194857, new BigDecimal(250));
		
		BigDecimal balance = accountService.getBalance(account.getId());

        assertEquals(0, new BigDecimal(250).compareTo(balance));
	}

	@Test
	void when_get10LastTransactions_given_accountWith11Transactions_then_10LastTransactionsReturned() {
		Account account = accountService.createAccount("Customer", 3847, 194857, new BigDecimal(0));
		
		List<Transaction> expectedTransactions = new ArrayList<>();

		transactionService.createTransaction(account.getId(), new BigDecimal(3));
		
		for (int i = 0; i < 10; i++) {
			Transaction transaction = transactionService.createTransaction(account.getId(), new BigDecimal((i + 1) * 10));
			expectedTransactions.add(transaction);
		}
		
		List<Transaction> transactions = transactionFinderService.getTenLastTransactions(account.getId())
				.stream()
				.sorted((t1, t2) -> t1.getId() > t2.getId() ? 1 : -1)
				.toList();
		
		expectedTransactions = expectedTransactions
				.stream()
				.sorted((t1, t2) -> t1.getId() > t2.getId() ? 1 : -1)
				.toList();
		
		assertIterableEquals(expectedTransactions, transactions);
	}
}
