package com.springbank.bankacc.query.api.controllers;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbank.bankacc.query.api.dtos.AccountLookupResponse;
import com.springbank.bankacc.query.api.dtos.EqualityType;
import com.springbank.bankacc.query.api.queries.FindAccountByHoderIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountByIdQuery;
import com.springbank.bankacc.query.api.queries.FindAccountWithBalanceQuery;
import com.springbank.bankacc.query.api.queries.FindAllAccountsQuery;

@RestController
@RequestMapping(path = "/api/v1/accountLookup")
public class BankAccountLookupController {

	private final QueryGateway queryGateway;

	@Autowired
	public BankAccountLookupController(QueryGateway queryGateway) {
		super();
		this.queryGateway = queryGateway;
	}

	@GetMapping("/")
	@PreAuthorize("hasAuthority('READ_PRIVILEGE')")
	public ResponseEntity<AccountLookupResponse> getAllAccounts() {
		try {
			var account = queryGateway.query(new FindAllAccountsQuery(), 
					AccountLookupResponse.class)
					.join();
			if (account == null || account.getAccounts() == null ) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(account,HttpStatus.OK);
		} catch (Exception e) {
			var safeErrorMessage = "error querying for all accounts!!!!!!";
			System.out.println(safeErrorMessage + "      " + e.getMessage());
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/byId/{id}")
	@PreAuthorize("hasAuthority('READ_PRIVILEGE')")
	public ResponseEntity<AccountLookupResponse> getAccountById(@PathVariable("id") String id) {
		try {
			var account = queryGateway.query(new FindAccountByIdQuery(id), 
					AccountLookupResponse.class)
					.join();
			if (account == null || account.getAccounts() == null ) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(account,HttpStatus.OK);
		} catch (Exception e) {
			var safeErrorMessage = "error querying accounts by account id!!!!!! : " +id ;
			System.out.println(safeErrorMessage + "      " + e.getMessage());
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/accountHolder/{id}")
	@PreAuthorize("hasAuthority('READ_PRIVILEGE')")
	public ResponseEntity<AccountLookupResponse> 
	       getAccountByAccountHolderId(@PathVariable("id") String id) {
		try {
			var account = queryGateway.query(new FindAccountByHoderIdQuery(id), 
					AccountLookupResponse.class)
					.join();
			if (account == null || account.getAccounts() == null ) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(account,HttpStatus.OK);
		} catch (Exception e) {
			var safeErrorMessage = "error querying accounts by account hoder id!!!!!! : "+id;
			System.out.println(safeErrorMessage + "      " + e.getMessage());
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/balance/{balance}/condition/{condition}")
	@PreAuthorize("hasAuthority('READ_PRIVILEGE')")
	public ResponseEntity<AccountLookupResponse> 
	       getAccountByBalance(@PathVariable("balance") double balance, 
	    	@PathVariable("condition")	EqualityType eq   ) {
		try {
			var account = queryGateway.query(FindAccountWithBalanceQuery
					.builder()
					.balance(balance)
					.condition(eq)
					.build(), 
					AccountLookupResponse.class)
					.join();
			if (account == null || account.getAccounts() == null ) {
				return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(account,HttpStatus.OK);
		} catch (Exception e) {
			var safeErrorMessage = "error querying accounts by balance !!!!!! : "+balance +" condition: "+eq ;
			System.out.println(safeErrorMessage + "      " + e.getMessage());
			return new ResponseEntity<AccountLookupResponse>(new AccountLookupResponse(safeErrorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
