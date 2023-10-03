package com.example.jetdevs.fileUploader.exception;

public class RecordNotExistException extends RuntimeException {
	public RecordNotExistException(String message) {
		super(message);
	}
}