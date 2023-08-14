package com.bank.accountapi.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import com.bank.accountapi.exceptionsuppliers.AccountNotFoundSupplier;
import com.bank.accountapi.repositories.AccountRepository;
import com.bank.accountapi.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountapi.models.Account;
import com.bank.accountapi.models.Transaction;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;
	private final TransactionRepository transactionRepository;
	
	public Account createAccount(
			String customerId,
			int registrationNumber,
			int accountNumber,
			BigDecimal startingBalance) {
		Account account = new Account(registrationNumber, accountNumber, customerId, startingBalance);
		
		if (startingBalance.signum() < 0)
			throw new IllegalArgumentException("Starting balance must be 0 or larger.");
		
		return accountRepository.save(account);
	}

	public BigDecimal getBalance(int accountId) {
		Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundSupplier.supplier);
		return account.getBalance();
	}
	

}
