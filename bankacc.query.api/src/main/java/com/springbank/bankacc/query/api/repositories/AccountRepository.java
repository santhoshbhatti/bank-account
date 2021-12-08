package com.springbank.bankacc.query.api.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbank.bankacc.core.models.BankAccount;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String>{

	List<BankAccount> findByAccountHolderId(String accontHolderId);
	List<BankAccount> findByBalanceGreaterThan(double balance);
	List<BankAccount> findByBalanceLessThan(double balance);
	
}
