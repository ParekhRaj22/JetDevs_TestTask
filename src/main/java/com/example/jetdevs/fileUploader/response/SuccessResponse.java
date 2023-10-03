package com.example.jetdevs.fileUploader.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SuccessResponse<T> {
	private String message;
	private T data;
}
