package com.amdocs.media.assignement.profile.service;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.amdocs.media.assignement.profile.entity.Profile;
import com.amdocs.media.assignement.profile.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Mono;

public interface ProfileService {
	public Mono<Profile> save(Profile profile);

	public void update(ProfileDTO profile) throws NotFoundException, JsonMappingException, JsonProcessingException;

	public void delete(ProfileDTO profileId);
}
