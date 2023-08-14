package com.bank.accountapi.services;

import com.bank.accountapi.exceptionsuppliers.AccountNotFoundSupplier;
import com.bank.accountapi.models.Account;
import com.bank.accountapi.models.Transaction;
import com.bank.accountapi.repositories.AccountRepository;
import com.bank.accountapi.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionFinderService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public List<Transaction> getTenLastTransactions(int accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundSupplier.supplier);
        return account.getTransactions()
                .stream()
                .sorted((t1, t2) -> t1.getCreated().isBefore(t2.getCreated()) ? 1 : -1)
                .limit(10)
                .toList();
    }
}
