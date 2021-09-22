package com.springionic.java.service.exception;

public class AuthorizationExcetion extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthorizationExcetion(String msg) {
		super(msg);
	}

	public AuthorizationExcetion(String msg, Throwable cause) {
		super(msg, cause);
	}
}
