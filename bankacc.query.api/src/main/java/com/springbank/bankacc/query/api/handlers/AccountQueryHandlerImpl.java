package com.springbank.bankacc.query.api.handlers;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbank.bankacc.core.models.BankAccount;
import com.springbank.bankacc.query.api.dtos.AccountLookupResponse;
import com.springbank.bankacc.query.api.dtos.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByHoderIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;
import com.springbank.bankacc.query.api.repositories.AccountRepository;
@Service
public class AccountQueryHandlerImpl implements AccountQueryHandler {
	
	
	AccountRepository accountRepository;
	
	@Autowired
	public AccountQueryHandlerImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@QueryHandler
	@Override
	public AccountLookupResponse findAccountById(FindAccountByIdQuery query) {
		var accountOpt = accountRepository.findById(query.getId());
		if (accountOpt.isPresent()) {
			var response = new AccountLookupResponse("bank account found",accountOpt.get());
			return response;
		}
		return  new AccountLookupResponse("no bank account found "+query.getId());
	}

	@QueryHandler
	@Override
	public AccountLookupResponse findAccountByHolderId(FindAccountByHoderIdQuery query) {
		var accounts = accountRepository.findByAccountHolderId(query.getAccontHolderId());
		if(accounts.size() > 0) {
			var response = new AccountLookupResponse("bank account found",accounts);
			return response;
		}
		
		return new AccountLookupResponse("No bank accounts found for the user "+query.getAccontHolderId());
	}

	@QueryHandler
	@Override
	public AccountLookupResponse findAllAccounts(FindAllAccountsQuery query) {
		var bankAccounts = accountRepository.findAll();
		if(bankAccounts.iterator().hasNext()) {
			var list = new ArrayList<BankAccount>();
			bankAccounts.forEach(acc -> list.add(acc));
			var response = new AccountLookupResponse("Bank accounts found",list);
			return response;
		}
		return  new AccountLookupResponse("No Bank accounts found");
	}

	@QueryHandler
	@Override
	public AccountLookupResponse findAllAccountsWithBalance(FindAccountWithBalanceQuery query) {
		List<BankAccount> accounts = null;
		if(query.getCondition().equals(EqualityType.GREATER_THAN)) {
			accounts = accountRepository.findByBalanceGreaterThan(query.getBalance());
		}else {
			accounts = accountRepository.findByBalanceLessThan(query.getBalance());
		}
		
		if (accounts.size() > 0) {
			return new AccountLookupResponse("Accounts with balance ",accounts);
		}
		return new AccountLookupResponse("No Accounts with balance ");
	}

	

}
