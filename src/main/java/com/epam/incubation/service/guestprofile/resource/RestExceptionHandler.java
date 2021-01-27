package com.epam.incubation.service.guestprofile.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.epam.incubation.service.guestprofile.datamodel.ApiError;
import com.epam.incubation.service.guestprofile.exception.GlobalException;
import com.epam.incubation.service.guestprofile.exception.RecordNotFoundException;

@ControllerAdvice
public class RestExceptionHandler {

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		String error = "Malformed JSON request with ";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error + ex.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<Object> globalExceptionHandling(Exception ex) {
		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex) {
		ApiError error = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(RecordNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND,ex.getMessage());
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		return buildResponseEntity(
				new ApiError(HttpStatus.BAD_REQUEST, "Validation Error", ex.getFieldErrors()));
	}
}
