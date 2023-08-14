package com.bank.accountapi.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@ManyToOne
	@EqualsAndHashCode.Exclude
	private Account account;
	@EqualsAndHashCode.Exclude
	private BigDecimal amount;
	@EqualsAndHashCode.Exclude
	private LocalDateTime created;
	
	public Transaction(Account account, BigDecimal amount) {
		this.account = account;
		this.amount = amount;
	}

	@PrePersist
	private void onCreate() {
		// Simplification: Remove nanoseconds so they don't cause rounding errors in the test
		created = LocalDateTime.now();
	}

	@EqualsAndHashCode.Include
	private double getAmountForEquals() {
		return amount != null ? amount.doubleValue() : 0;
	}
	
	@EqualsAndHashCode.Include
	private int getAccountIdForEquals() {
		return account.getId();
	}

	@EqualsAndHashCode.Include
	private String getCreatedForEquals() {
		return created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
}
