package com.bank.accountapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountapi.models.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
