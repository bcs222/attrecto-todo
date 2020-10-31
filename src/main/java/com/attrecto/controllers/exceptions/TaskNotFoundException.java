package com.attrecto.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public TaskNotFoundException() {
		super("Task not found...");
	}
	
	public TaskNotFoundException(Number id) {
		super("Task not found... ID: " + id);
	}
	
	public TaskNotFoundException(String message) {
		super(message);
	}
	
	public TaskNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TaskNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
