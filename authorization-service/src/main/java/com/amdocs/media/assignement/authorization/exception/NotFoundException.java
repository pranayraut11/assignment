package com.amdocs.media.assignement.authorization.exception;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String GENERIC_MESSAGE = "Entity not found";
	private static final String MESSAGE = " not found";

	public NotFoundException() {
		super(GENERIC_MESSAGE);
		// TODO Auto-generated constructor stub
	}

	public NotFoundException(String arg0) {
		super(arg0 + MESSAGE);
		// TODO Auto-generated constructor stub
	}

}
