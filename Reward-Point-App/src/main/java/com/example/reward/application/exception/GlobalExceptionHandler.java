package com.example.reward.application.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> haandleCustomerNotFoundEexception(CustomerNotFoundException ex) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("Message", "Customer not found");

		errorDetails.put("status", HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put("Message", ex.getMessage());

		errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidTransactionAmountException.class)
	public ResponseEntity<Object> invalidTransactionAmountException(InvalidTransactionAmountException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
