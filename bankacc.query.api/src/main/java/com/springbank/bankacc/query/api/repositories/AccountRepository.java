package com.springbank.bankacc.query.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springbank.bankacc.core.models.BankAccount;

@Repository
public interface AccountRepository extends CrudRepository<BankAccount, String>{

}
