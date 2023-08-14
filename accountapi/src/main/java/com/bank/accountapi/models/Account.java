package com.bank.accountapi.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@EqualsAndHashCode.Exclude
	private Integer id;
	private String customerId;
	private int registrationNumber;
	private int accountNumber;
	// Simplification: This bank only deals with USD
	@EqualsAndHashCode.Exclude
	private BigDecimal balance;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
	@ToString.Exclude
	private List<Transaction> transactions = new ArrayList<>();
	
	public Account(int registrationNumber, int accountNumber, String customerId, BigDecimal startingBalance) {
		this.registrationNumber = registrationNumber;
		this.accountNumber = accountNumber;
		this.customerId = customerId;
		this.balance = startingBalance;
	}
}
