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
	private List<FieldError> subErrors;

	public ApiError() {
		timestamp = LocalDateTime.now();
	}

	public ApiError(HttpStatus status, String message, List<FieldError> subErrors) {
		this();
		this.status = status;
		this.message = message;
		this.subErrors = subErrors;
	}
	
	public ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
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

	public List<FieldError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<FieldError> subErrors) {
		this.subErrors = subErrors;
	}
}
