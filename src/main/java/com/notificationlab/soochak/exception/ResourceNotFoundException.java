package com.notificationlab.soochak.exception;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
		super();
	}
	
	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
	
	public ResourceNotFoundException(final String msg) {
		super(msg);
	}
	
	public ResourceNotFoundException(final String msg, Throwable cause) {
		super(msg);
	}
}
