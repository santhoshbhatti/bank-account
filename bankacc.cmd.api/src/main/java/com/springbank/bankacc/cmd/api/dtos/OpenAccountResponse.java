package com.springbank.bankacc.cmd.api.dtos;

import com.springbank.bankacc.core.dto.BaseResponse;

import lombok.Data;

@Data
public class OpenAccountResponse extends BaseResponse{

	private String id;
	public OpenAccountResponse(String id,String message) {
		super(message);
		this.id = id;
	}

}
