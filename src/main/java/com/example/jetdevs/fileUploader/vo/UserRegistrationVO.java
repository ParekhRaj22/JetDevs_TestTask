package com.example.jetdevs.fileUploader.vo;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistrationVO {
	@NotEmpty(message = "Name cannot be null or empty")
	private String name;

	@NotEmpty(message = "Password cannot be null or empty")
	private String password;

	@NotEmpty(message = "Role should not be null or empty")
	private String role;
}
