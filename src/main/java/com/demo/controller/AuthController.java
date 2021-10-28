package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.request.AuthRequest;
import com.demo.dto.response.AuthResponse;
import com.demo.entity.User;
import com.demo.security.JwtTokenService;
import com.demo.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private UserService userService;

	@PostMapping("/authenticate")
	public AuthResponse authenticateUser(@RequestBody AuthRequest authRequest) {
		final User user = userService.findByUsername(authRequest.getUsername());
		authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		return new AuthResponse(jwtTokenService.generateToken(user.getUsername()));
	}
}
