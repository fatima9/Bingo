package com.example.bingo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.bingo.model.ErrorModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class BingoServiceControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BingoServiceValidationException.class)
	public ResponseEntity<?> handleConstraintViolationError(BingoServiceValidationException ex) {

		log.error("Bad request for bingo service", ex);

		ErrorModel response = new ErrorModel(String.valueOf(HttpStatus.BAD_REQUEST), ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BingoServiceException.class)
	public ResponseEntity<?> handleException(BingoServiceException ex) {

		log.error("An exception has occurred for bingo service", ex);

		ErrorModel response = new ErrorModel(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR), ex.getMessage());

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
