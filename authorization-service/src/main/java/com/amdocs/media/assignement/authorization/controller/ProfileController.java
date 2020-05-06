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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.amdocs.media.assignement.authorization.service.ProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileServiceImpl;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Mono<ProfileDTO> createProfile(@RequestBody ProfileDTO profile) {
		return profileServiceImpl.save(profile);
	}

	@PutMapping
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public ResponseEntity<Void> updateProfile(@RequestBody ProfileDTO profile)
			throws NotFoundException, JsonProcessingException {
		profileServiceImpl.update(profile);
		return ResponseEntity.accepted().build();
	}

	@DeleteMapping
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public ResponseEntity<Void> deletProfile(@RequestParam int userId) throws JsonProcessingException {
		profileServiceImpl.delete(userId);
		return ResponseEntity.accepted().build();
	}

}
