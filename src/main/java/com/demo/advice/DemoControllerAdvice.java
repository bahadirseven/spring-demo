package com.demo.advice;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.dto.response.ExceptionResponse;
import com.demo.exception.UserNotFoundException;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@ControllerAdvice
public class DemoControllerAdvice {

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleException(UserNotFoundException exception) {
		ExceptionResponse response = new ExceptionResponse(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleException(EntityNotFoundException exception) {
		ExceptionResponse response = new ExceptionResponse(exception.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = SignatureException.class)
	public ResponseEntity<ExceptionResponse> handleException(SignatureException exception) {
		ExceptionResponse response = new ExceptionResponse("Token signature is not verified.");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
		ExceptionResponse response = new ExceptionResponse("An unexpected error was encountered");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = MalformedJwtException.class)
	public ResponseEntity<ExceptionResponse> handleException(MalformedJwtException exception) {
		ExceptionResponse response = new ExceptionResponse("Invalid token format.");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}