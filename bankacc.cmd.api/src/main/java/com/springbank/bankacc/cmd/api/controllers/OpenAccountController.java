package com.springbank.bankacc.cmd.api.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbank.bankacc.cmd.api.commands.OpenAccountCommand;
import com.springbank.bankacc.cmd.api.dtos.OpenAccountResponse;

@RestController
@RequestMapping(path = "/api/v1/openBankAccount")
public class OpenAccountController {
	private final CommandGateway commandGateway;

	@Autowired
	public OpenAccountController(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}

	@PostMapping
	@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
	public ResponseEntity<OpenAccountResponse> openAccount(@Valid @RequestBody OpenAccountCommand command) {
		try {
			var uuid = UUID.randomUUID().toString();
			command.setId(uuid);
			commandGateway.send(command);
			return new ResponseEntity<OpenAccountResponse>(
					new OpenAccountResponse(uuid, "Bank Account created successfully"),
					HttpStatus.CREATED);
		} catch (Exception e) {
			String safeMessage = "error creating bank account";
			System.out.print("ERROR!!!!! creating bank account  "+e.toString());
			return new ResponseEntity<OpenAccountResponse>(
					new OpenAccountResponse(null, safeMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
