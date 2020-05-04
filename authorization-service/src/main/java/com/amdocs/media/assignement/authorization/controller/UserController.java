package com.amdocs.media.assignement.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.amdocs.media.assignement.authorization.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class UserController {

	@Autowired
	private ProfileService profileService;

	@GetMapping("generate")
	public String generate() throws NotFoundException, JsonProcessingException {
		ProfileDTO profile = new ProfileDTO();
		profile.setUserId(1);
		profile.setAddress("Address");
		profile.setMobile("Mobile");
		profileService.update(profile);
		return "pranay";
	}

}
