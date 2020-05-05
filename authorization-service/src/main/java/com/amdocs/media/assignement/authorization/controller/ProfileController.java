package com.amdocs.media.assignement.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Mono<ProfileDTO> createProfile(@RequestBody ProfileDTO profile) {
		return profileService.save(profile);
	}

	@PutMapping
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public Mono<Disposable> updateProfile(@RequestBody ProfileDTO profile)
			throws NotFoundException, JsonProcessingException {
		return profileService.update(profile);
	}

	@DeleteMapping
	@ResponseStatus(value = HttpStatus.ACCEPTED)
	public Mono<Disposable> deletProfile(@RequestParam int userId) throws JsonProcessingException {
		return profileService.delete(userId);
	}

}
