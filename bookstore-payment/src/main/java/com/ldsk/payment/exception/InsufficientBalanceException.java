package com.ldsk.payment.exception;

public class InsufficientBalanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "Customer does not have enough balance";
	
	public InsufficientBalanceException() {
		super(MESSAGE);
	}
	
}
