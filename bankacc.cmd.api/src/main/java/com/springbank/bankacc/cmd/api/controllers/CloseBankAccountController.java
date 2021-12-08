package com.springbank.bankacc.cmd.api.controllers;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbank.bankacc.cmd.api.commands.CloseAccountCommand;
import com.springbank.bankacc.cmd.api.dtos.OpenAccountResponse;
import com.springbank.bankacc.core.dto.BaseResponse;

@RestController
@RequestMapping(path = "/api/v1/closeBankAccount")
public class CloseBankAccountController {

	private final CommandGateway commandGateway;

	@Autowired
	public CloseBankAccountController(CommandGateway commandGateway) {
		super();
		this.commandGateway = commandGateway;
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<BaseResponse> closeBankAccount(@PathVariable String id) {
		try {
			var command = new CloseAccountCommand();
			command.setId(id);
			commandGateway.send(command).get();
			return new ResponseEntity<BaseResponse>(new BaseResponse(id + " account successfully closed"),
					HttpStatus.OK);
		} catch (Exception e) {
			String safeMessage = "error while closing bank account";
			System.out.print("ERROR!!!!! closing bank account  " + id + "  " + e.toString());
			return new ResponseEntity<BaseResponse>(new OpenAccountResponse(null, safeMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
