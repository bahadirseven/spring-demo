package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.request.UserRequest;
import com.demo.dto.response.UserResponse;
import com.demo.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users/{id}")
	public UserResponse getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
	}

	@PostMapping("/users")
	public UserResponse createUser(@RequestBody UserRequest userRequest) {
		return userService.createOrUpdateUser(userRequest);
	}

	@PutMapping("/users")
	public UserResponse updateUser(@RequestBody UserRequest userRequest) {
		return userService.createOrUpdateUser(userRequest);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
		return ResponseEntity.ok("Deleted Successfully.");
	}
}
