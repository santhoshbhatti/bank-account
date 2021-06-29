package com.springbank.bankacc.core.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BankAccount {
	
	@Id
	private String id;
	
	private String accountHolderId;
	private Date creationDate;
	private AccountType accoutnType;
	private double balance;

}
