package com.amdocs.media.assignement.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.amdocs.media.assignement.authorization.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping
	public ResponseEntity<Void> createProfile(@RequestBody ProfileDTO profile) {
		profileService.save(profile);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping
	public ResponseEntity<Void> updateProfile(@RequestBody ProfileDTO profile)
			throws NotFoundException, JsonProcessingException {
		profileService.update(profile);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deletProfile(@RequestParam int userId) {
		profileService.delete(userId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

}
