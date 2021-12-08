package com.springbank.bankacc.query.api.queries;

import com.springbank.bankacc.query.api.dtos.EqualityType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindAccountWithBalanceQuery {
	
	private EqualityType condition;
	private double balance;

}
