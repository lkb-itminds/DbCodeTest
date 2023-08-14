package com.bank.accountapi.models.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionDto {
	private final int accountId;
	private final BigDecimal amount;
}
