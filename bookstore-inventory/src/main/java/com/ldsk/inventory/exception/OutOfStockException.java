package com.ldsk.inventory.exception;

public class OutOfStockException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "Out of stock";
	
	public OutOfStockException() {
		super(MESSAGE);
	}

}
