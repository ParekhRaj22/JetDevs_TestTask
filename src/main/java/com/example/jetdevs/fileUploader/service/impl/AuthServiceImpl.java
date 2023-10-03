package com.example.jetdevs.fileUploader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.jetdevs.fileUploader.domain.Role;
import com.example.jetdevs.fileUploader.domain.User;
import com.example.jetdevs.fileUploader.exception.InvalidCredentialException;
import com.example.jetdevs.fileUploader.exception.RecordAlreadyExistException;
import com.example.jetdevs.fileUploader.repository.RoleRepository;
import com.example.jetdevs.fileUploader.repository.UserRepository;
import com.example.jetdevs.fileUploader.security.JwtTokenProvider;
import com.example.jetdevs.fileUploader.service.AuthService;
import com.example.jetdevs.fileUploader.vo.LoginVO;
import com.example.jetdevs.fileUploader.vo.UserRegistrationVO;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public String login(LoginVO loginDto) throws InvalidCredentialException {
		validateLoginDto(loginDto);
		String token = "";
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			token = jwtTokenProvider.generateToken(authentication);
		} catch (Exception e) {
			throw new InvalidCredentialException("Invalid login credential, please try again");
		}
		return token;
	}

	@Override
	public User registerUser(UserRegistrationVO userDto) {
		boolean isUserExist = userRepository.findByUsername(userDto.getName()).isPresent();

		if (isUserExist) {
			throw new RecordAlreadyExistException("User already exist");
		}

		Role userRole = roleRepository.findByName(userDto.getRole().toUpperCase())
				.orElseThrow(() -> new RuntimeException("Invalid role"));
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);

		User user = new User();
		user.setUsername(userDto.getName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setRoles(roles);

		return userRepository.save(user);
	}

	@Override
	public User getUserByUserName(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
	}

	private void validateLoginDto(LoginVO loginDto) throws InvalidCredentialException {
		if (StringUtils.isEmpty(loginDto.getUsername()) || StringUtils.isEmpty(loginDto.getPassword())) {
			throw new InvalidCredentialException("Invalid login credential, please try again");
		}
	}
}
