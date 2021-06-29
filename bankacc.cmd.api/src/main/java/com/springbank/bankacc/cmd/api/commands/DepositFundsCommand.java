package com.springbank.bankacc.cmd.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositFundsCommand {
	
	@TargetAggregateIdentifier
	private String id;
	
	private double amount;

}
