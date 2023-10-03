package com.example.jetdevs.fileUploader.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.jetdevs.fileUploader.response.ErrorResponse;
import com.example.jetdevs.fileUploader.response.SuccessResponse;

public class BaseController<T> {
	protected ResponseEntity<SuccessResponse> success(T data, String message) {
		SuccessResponse<T> successResponse = new SuccessResponse<T>();
		successResponse.setData(data);
		successResponse.setMessage(message);
		return new ResponseEntity<SuccessResponse>(successResponse, HttpStatus.OK);
	}

	protected ResponseEntity<ErrorResponse> error(String message, HttpStatus httpStatus, String path) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimeStamp(new Date());
		errorResponse.setMessage(message);
		errorResponse.setStatusCode(httpStatus.value());
		errorResponse.setPath(path);
		return new ResponseEntity<>(errorResponse, httpStatus);
	}
}
