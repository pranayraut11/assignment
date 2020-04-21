package com.amdocs.media.assignement.authorization.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;

@RestController
@RequestMapping("profile")
public class ProfileController {

	@PostMapping
	public void createProfile(@RequestBody ProfileDTO profile) {
		System.out.println(profile);
	}

}
