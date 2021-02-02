package com.epam.incubation.service.guestprofile.responsemodel;

import org.springframework.http.HttpStatus;

import com.epam.incubation.service.guestprofile.datamodel.ApiError;

public class GuestResponseModel<T> {

	private T data;
	private ApiError error;
	private HttpStatus status;

	public GuestResponseModel() {
	}

	public GuestResponseModel(T data, ApiError error, HttpStatus status) {
		this.data = data;
		this.error = error;
		this.status = status;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ApiError getError() {
		return error;
	}

	public void setError(ApiError error) {
		this.error = error;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
