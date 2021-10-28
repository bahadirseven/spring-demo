package com.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.response.EchoResponse;

@RestController
public class EchoController {

	@GetMapping("/echo")
	public EchoResponse echo() {
		return new EchoResponse("Welcome to Demo Services...");
	}
}
