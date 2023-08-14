package com.bank.accountapi.exceptionsuppliers;

import jakarta.persistence.EntityNotFoundException;

import java.util.function.Supplier;

public class AccountNotFoundSupplier {
    public static final Supplier<EntityNotFoundException> supplier = () -> new EntityNotFoundException("Account wasn't found");
}
