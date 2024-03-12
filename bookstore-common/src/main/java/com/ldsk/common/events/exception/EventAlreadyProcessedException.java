package com.ldsk.common.events.exception;

public class EventAlreadyProcessedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private static final String MESSAGE = "The event is already processed";
	
	public EventAlreadyProcessedException() {
		super(MESSAGE);
	}
	
}
