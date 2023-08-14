package com.bank.accountapi.models.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class TransactionDto {

	private final int accountId;
	private final BigDecimal amount;
	private final LocalDateTime created;
}
