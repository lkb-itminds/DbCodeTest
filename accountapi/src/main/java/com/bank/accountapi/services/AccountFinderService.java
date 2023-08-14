package com.bank.accountapi.services;

import com.bank.accountapi.exceptionsuppliers.AccountNotFoundSupplier;
import com.bank.accountapi.models.Account;
import com.bank.accountapi.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountFinderService {

    private final AccountRepository accountRepository;

    public Account getAccount(int accountId) {
        return accountRepository.findById(accountId).orElseThrow(AccountNotFoundSupplier.supplier);
    }
}
