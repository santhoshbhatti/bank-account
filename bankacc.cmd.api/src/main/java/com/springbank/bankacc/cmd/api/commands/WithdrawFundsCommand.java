package com.springbank.bankacc.cmd.api.commands;

import javax.validation.constraints.Min;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawFundsCommand {
	
	@TargetAggregateIdentifier
	private String id;

	@Min(value = 1, message = "invalid withdraw value")
	private double amount;
}
