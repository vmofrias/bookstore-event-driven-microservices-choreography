package com.ldsk.payment.exception;

public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "Customer not found";
	
	public CustomerNotFoundException() {
		super(MESSAGE);
	}
	
}
