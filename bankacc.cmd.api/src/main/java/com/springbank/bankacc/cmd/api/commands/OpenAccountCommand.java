package com.springbank.bankacc.cmd.api.commands;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.springbank.bankacc.core.models.AccountType;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenAccountCommand {
	
	@TargetAggregateIdentifier
	private String id;
	
	@NotNull(message = "no account holder id was supplied")
	private String accountHolderId;
	
	@NotNull(message = "no account type was supplied")
	private AccountType accountType;
	
	@Min(value = 0, message= "balance cannot be negative")
	private double openingBalance;
}
