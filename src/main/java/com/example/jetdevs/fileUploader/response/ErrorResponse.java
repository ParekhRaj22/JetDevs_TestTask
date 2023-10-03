package com.example.jetdevs.fileUploader.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {
	private Date timeStamp;
	private String message;
	private int statusCode;
	private String path;
}
