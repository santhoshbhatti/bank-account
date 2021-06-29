package com.springbank.bankacc.core.events;

import java.util.Date;

import com.springbank.bankacc.core.models.AccountType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountOpenedEvent {

	private String id;
	private String accountHolderId;
	private AccountType accountType;
	private double openingBalance;
	private Date creationDate;
	
}
