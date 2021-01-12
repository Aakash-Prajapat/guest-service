package com.epam.incubation.service.guestprofile.datamodel;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String details;
	private List<FieldError> subErrors;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.details = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.details = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, String message, String details, List<FieldError> subErrors) {
		this();
		this.status = status;
		this.message = message;
		this.details = details;
		this.subErrors = subErrors;
	}

	public ApiError(HttpStatus status, String message, String details) {
		this();
		this.status = status;
		this.message = message;
		this.details = details;
	}
	
	public ApiError(HttpStatus status, String message, List<FieldError> subErrors) {
		this();
		this.status = status;
		this.message = message;
		this.subErrors = subErrors;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<FieldError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<FieldError> subErrors) {
		this.subErrors = subErrors;
	}
}
