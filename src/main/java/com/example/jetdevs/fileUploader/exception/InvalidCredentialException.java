package com.example.jetdevs.fileUploader.exception;

public class InvalidCredentialException extends RuntimeException {
	public InvalidCredentialException(String message) {
		super(message);
	}
}