package com.example.jetdevs.fileUploader.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.jetdevs.fileUploader.security.JwtEntryPoint;
import com.example.jetdevs.fileUploader.security.JwtFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	public static final String[] PUBLIC_URL = {
			"/api/auth/**",
			"/v2/api-docs",
			"/v3/api-docs",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/webjars/**",
	};

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtFilter authenticationFilter;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests(
				(authorize) -> authorize.requestMatchers(PUBLIC_URL).permitAll().anyRequest().authenticated())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
