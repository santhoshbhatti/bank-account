package com.springbank.bankacc.query.api.handlers;

import com.springbank.bankacc.query.api.dtos.AccountLookupResponse;
import com.springbank.bankacc.query.api.queries.FindAccountByHoderIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;

public interface AccountQueryHandler {
	
	AccountLookupResponse findAccountById(FindAccountByIdQuery query);
	
	AccountLookupResponse findAccountByHolderId(FindAccountByHoderIdQuery query);
	
	AccountLookupResponse findAllAccounts(FindAllAccountsQuery query);
	
	AccountLookupResponse findAllAccountsWithBalance(FindAccountWithBalanceQuery query);

}
