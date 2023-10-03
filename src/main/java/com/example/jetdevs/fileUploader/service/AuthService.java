package com.example.jetdevs.fileUploader.service;

import org.springframework.stereotype.Component;

import com.example.jetdevs.fileUploader.domain.User;
import com.example.jetdevs.fileUploader.exception.InvalidCredentialException;
import com.example.jetdevs.fileUploader.vo.LoginVO;
import com.example.jetdevs.fileUploader.vo.UserRegistrationVO;

@Component
public interface AuthService {
	String login(LoginVO loginDto) throws InvalidCredentialException;

	User registerUser(UserRegistrationVO userDto);

	User getUserByUserName(String username);
}
