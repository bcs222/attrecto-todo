package com.attrecto.controllers.exceptions;

import java.util.Map;

public final class ValidationErrorMessage extends ErrorMessage{
	private final Map<String, String> map;
	
	public ValidationErrorMessage(Map<String, String> map) {
		super("Validation error...");
		this.map = map;
	}

	public Map<String, String> getMap() {
		return map;
	}
	
}
