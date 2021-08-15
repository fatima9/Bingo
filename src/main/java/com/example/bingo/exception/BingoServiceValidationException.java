package com.example.bingo.exception;

public class BingoServiceValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BingoServiceValidationException() {
		super();
	}
	
	public BingoServiceValidationException(String message) {
		super(message);
	}
}
