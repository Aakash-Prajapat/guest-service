package com.epam.incubation.service.guestprofile.model;

import org.springframework.http.HttpStatus;

import com.epam.incubation.service.guestprofile.datamodel.ApiError;

public class ApiResponse<T extends Object> {

	private T data;
	private HttpStatus status;
	private ApiError error;

	public ApiResponse() {
	}
	public ApiResponse(T data, HttpStatus status, ApiError error) {
		this.data = data;
		this.status = status;
		this.error = error;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public ApiError getError() {
		return error;
	}
	public void setError(ApiError error) {
		this.error = error;
	}
}
