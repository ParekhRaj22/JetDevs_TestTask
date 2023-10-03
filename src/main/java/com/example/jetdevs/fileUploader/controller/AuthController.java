package com.example.jetdevs.fileUploader.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jetdevs.fileUploader.response.SuccessResponse;
import com.example.jetdevs.fileUploader.service.AuthService;
import com.example.jetdevs.fileUploader.vo.AuthResponse;
import com.example.jetdevs.fileUploader.vo.LoginVO;
import com.example.jetdevs.fileUploader.vo.UserRegistrationVO;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

	@Autowired
	private AuthService authService;
	
	
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse<AuthResponse>> authenticateUser(@Valid @RequestBody LoginVO loginDto) {
		String token = authService.login(loginDto);
		AuthResponse authResponse = new AuthResponse(token);
		return success(authResponse, "Token for user");
	}

	
	@PostMapping(value = { "/register", "/signup" })
	public ResponseEntity<SuccessResponse> register(@Valid @RequestBody UserRegistrationVO user) {
		return success(authService.registerUser(user), "User is registered");
	}
}
