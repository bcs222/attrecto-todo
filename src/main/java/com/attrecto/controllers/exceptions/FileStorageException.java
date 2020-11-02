package com.attrecto.controllers.exceptions;

public class FileStorageException extends RuntimeException{
private static final long serialVersionUID = 1L;
	
	public FileStorageException() {
		super("Task not found...");
	}
	
	public FileStorageException(Number id) {
		super("Task not found... ID: " + id);
	}
	
	public FileStorageException(String message) {
		super(message);
	}
	
	public FileStorageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FileStorageException(Throwable cause) {
		super(cause);
	}
}
