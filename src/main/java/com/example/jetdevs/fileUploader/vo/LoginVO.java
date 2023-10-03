package com.example.jetdevs.fileUploader.vo;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
	@NotEmpty(message = "Username cannot be null or empty")
	private String username;

	@NotEmpty(message = "Password cannot be null or empty")
	private String password;
}
