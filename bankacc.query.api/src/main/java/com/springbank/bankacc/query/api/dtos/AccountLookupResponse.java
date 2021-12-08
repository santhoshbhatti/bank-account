package com.springbank.bankacc.query.api.dtos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.springbank.bankacc.core.dto.BaseResponse;
import com.springbank.bankacc.core.models.BankAccount;

public class AccountLookupResponse extends BaseResponse{

	private List<BankAccount> accounts;
	
	public AccountLookupResponse(String message) {
		super(message);
		
	}
	
	public AccountLookupResponse(String message, List<BankAccount> accounts) {
		super(message);
		this.accounts = accounts;
	}


	public AccountLookupResponse(String message, BankAccount account) {
		super(message);
		this.accounts = Arrays.asList(account);
		
	}

	public List<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<BankAccount> accounts) {
		this.accounts = accounts;
	}
	
	
}
