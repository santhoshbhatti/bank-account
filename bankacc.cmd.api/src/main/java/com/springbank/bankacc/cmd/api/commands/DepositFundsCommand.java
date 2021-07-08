package com.springbank.bankacc.cmd.api.commands;

import javax.validation.constraints.Min;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositFundsCommand {
	
	@TargetAggregateIdentifier
	private String id;
	
	@Min(value = 1, message = "deposit amount must be atleast 1 rupees")
	private double amount;

}
