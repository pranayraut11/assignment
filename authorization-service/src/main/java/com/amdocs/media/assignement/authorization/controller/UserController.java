package com.amdocs.media.assignement.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("generate")
	public String generate() {
		return passwordEncoder.encode("123");
	}

}
