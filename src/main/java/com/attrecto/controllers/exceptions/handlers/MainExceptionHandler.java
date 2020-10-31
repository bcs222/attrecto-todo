package com.attrecto.controllers.exceptions.handlers;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.attrecto.controllers.exceptions.ErrorMessage;
import com.attrecto.controllers.exceptions.TaskNotFoundException;
import com.attrecto.controllers.exceptions.UserNotFoundException;
import com.attrecto.controllers.exceptions.ValidationErrorMessage;

@ControllerAdvice
public class MainExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErrorMessage> handleValidationExceptions(
			MethodArgumentNotValidException exception, WebRequest request) {
		
		Map<String, String> map = exception.getBindingResult()
			.getAllErrors()
			.stream()
			.map(e -> (FieldError)e)
			.collect(
				Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)
			);
		
		return new ResponseEntity<>(
			new ValidationErrorMessage(map), 
			new HttpHeaders(), 
			HttpStatus.BAD_REQUEST
		);
	}
	
	@ExceptionHandler(UserNotFoundException.class) 
	public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception, WebRequest request){
		return new ResponseEntity<>(
			new ErrorMessage(exception.getMessage()), 
			new HttpHeaders(), 
			HttpStatus.BAD_REQUEST
		);
	}
	
	@ExceptionHandler(TaskNotFoundException.class) 
	public ResponseEntity<ErrorMessage> handleTaskNotFoundException(TaskNotFoundException exception, WebRequest request){
		return new ResponseEntity<>(
			new ErrorMessage(exception.getMessage()), 
			new HttpHeaders(), 
			HttpStatus.BAD_REQUEST
		);
	}
}
