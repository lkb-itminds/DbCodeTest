package com.bank.accountapi.services;

import com.bank.accountapi.exceptionsuppliers.AccountNotFoundSupplier;
import com.bank.accountapi.models.Account;
import com.bank.accountapi.models.Transaction;
import com.bank.accountapi.repositories.AccountRepository;
import com.bank.accountapi.repositories.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Supplier;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Transaction createTransaction(
            int accountId,
            BigDecimal amount) {

        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundSupplier.supplier);
        account.setBalance(account.getBalance().add(amount));

        if (account.getBalance().signum() < 0) {
            throw new IllegalStateException("Withdrawn amount was larger than current balance");
        }

        Transaction transaction = new Transaction(account, amount);
        account.getTransactions().add(transaction);

        transactionRepository.save(transaction);
        accountRepository.save(account);

        return transaction;
    }


}
