package com.bank.accountapi.controllers;

import java.math.BigDecimal;
import java.util.List;

import com.bank.accountapi.services.AccountFinderService;
import com.bank.accountapi.services.TransactionFinderService;
import com.bank.accountapi.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.accountapi.models.Account;
import com.bank.accountapi.models.Transaction;
import com.bank.accountapi.models.dtos.CreateAccountDto;
import com.bank.accountapi.models.dtos.CreateTransactionDto;
import com.bank.accountapi.models.dtos.TransactionDto;
import com.bank.accountapi.services.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;
	private final AccountFinderService accountFinderService;
	private final TransactionService transactionService;
	private final TransactionFinderService transactionFinderService;
	
	@PostMapping("/create-account")
	public Account createAccount(@RequestBody CreateAccountDto dto) {
		return accountService.createAccount(
				dto.getCustomerId(),
				dto.getRegistrationNumber(),
				dto.getAccountNumber(),
				new BigDecimal(dto.getStartingBalance()));
	}
	
	@PostMapping("/create-transaction")
	public void createTransaction(@RequestBody CreateTransactionDto dto) {
		transactionService.createTransaction(dto.getAccountId(), dto.getAmount());
	}
	
	@GetMapping("/get-balance/{accountId}")
	public BigDecimal getBalance(@PathVariable int accountId) {
		return accountService.getBalance(accountId);
	}
	
	@GetMapping("/get-ten-last-transactions/{accountId}")
	public List<TransactionDto> getTenLastTransactions(@PathVariable int accountId) {
		List<Transaction> transactions = transactionFinderService.getTenLastTransactions(accountId);

		return transactions
			.stream()
			.map(t -> new TransactionDto(t.getAccount().getId(), t.getAmount(), t.getCreated()))
			.toList();
	}
}
