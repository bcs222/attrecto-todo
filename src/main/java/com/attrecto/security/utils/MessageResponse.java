package com.attrecto.security.utils;

import java.io.Serializable;

public class MessageResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public MessageResponse() {}

	public MessageResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
