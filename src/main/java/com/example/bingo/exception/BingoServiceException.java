package com.example.bingo.exception;

public class BingoServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BingoServiceException() {
		super();
	}
	
	public BingoServiceException(String message) {
		super(message);
	}
}
