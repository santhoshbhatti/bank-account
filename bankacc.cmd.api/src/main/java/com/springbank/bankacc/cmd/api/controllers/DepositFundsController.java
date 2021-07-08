package com.springbank.bankacc.cmd.api.controllers;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbank.bankacc.cmd.api.commands.DepositFundsCommand;
import com.springbank.bankacc.cmd.api.dtos.OpenAccountResponse;
import com.springbank.bankacc.core.dto.BaseResponse;

@RestController
@RequestMapping(path = "/api/v1/depositFunds")
public class DepositFundsController {
	private final CommandGateway commandGateway;

	@Autowired
	public DepositFundsController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@PutMapping("/{accountid}")
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<BaseResponse> depositFunds(@PathVariable("accountid") String accountId,
			@Valid @RequestBody DepositFundsCommand command){
		
		try {
			command.setId(accountId);
			commandGateway.send(command);
			return new ResponseEntity<BaseResponse>(
					new BaseResponse(accountId+" has been successfully deposited")
					, HttpStatus.OK);
			
		}catch(Exception e) {
			String safeMessage = "error depositing funds to bank account";
			System.out.print("ERROR!!!!! creating bank account  "+accountId+"  "+e.toString());
			return new ResponseEntity<BaseResponse>(
					new OpenAccountResponse(null, safeMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
