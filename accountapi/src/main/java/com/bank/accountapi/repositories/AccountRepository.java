package com.bank.accountapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountapi.models.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

}
