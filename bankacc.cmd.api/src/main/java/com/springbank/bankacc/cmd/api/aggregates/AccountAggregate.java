package com.springbank.bankacc.cmd.api.aggregates;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.springbank.bankacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.bankacc.cmd.api.commands.DepositFundsCommand;
import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.cmd.api.commands.WithdrawFundsCommand;
import com.springbank.bankacc.core.events.AccountClosedEvent;
import com.springbank.bankacc.core.events.AccountOpenedEvent;
import com.springbank.bankacc.core.events.FundsDepositedEvent;
import com.springbank.bankacc.core.events.FundsWithdrawnEvent;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class AccountAggregate {

	@AggregateIdentifier
	private String id;
	private String accountHolderId;
	private double balance;
	
	@CommandHandler
	public AccountAggregate(OpenAccountCommand command) {
		var event = AccountOpenedEvent.builder()
				.id(command.getId())
				.accountHolderId(command.getAccountHolderId())
				.openingBalance(command.getOpeningBalance())
				.accountType(command.getAccountType())
				.creationDate(new Date())
				.build();
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(AccountOpenedEvent event) {
		this.id = event.getId();
		this.accountHolderId = event.getAccountHolderId();
		this.balance = event.getOpeningBalance();
	}
	
	@CommandHandler
	public void handle(DepositFundsCommand command) {
		var event = FundsDepositedEvent.builder()
				.id(command.getId())
				.amount(command.getAmount())
				.balance(this.balance + command.getAmount())
				.build();
		AggregateLifecycle.apply(event);
				
	}
	
	@EventSourcingHandler
	public void on(FundsDepositedEvent event) {
		this.balance = this.balance+event.getAmount();
		
	}
	
	@CommandHandler
	public void handle(WithdrawFundsCommand command) {
		var amount = command.getAmount();
		if(this.balance - amount < 0) {
			throw new IllegalStateException("Withdrawal declined . Insufficient funds ");
		}
		var event = FundsWithdrawnEvent.builder()
				.id(command.getId())
				.amount(command.getAmount())
				.balance(this.balance - amount)
				.build();
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(FundsWithdrawnEvent event) {
		
		this.balance = this.balance - event.getAmount();
	}
	
	@CommandHandler
	public void handler(CloseAccountCommand command) {
		var event = AccountClosedEvent.builder()
				.id(command.getId())
				.build();
		
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(AccountClosedEvent event) {
		AggregateLifecycle.markDeleted();
	}
}
