package com.bank.accountapi.models.dtos;

import lombok.Data;

@Data
public class CreateAccountDto {
	
	private final String customerId;
	private final int registrationNumber;
	private final int accountNumber;
	private final int startingBalance;
}
