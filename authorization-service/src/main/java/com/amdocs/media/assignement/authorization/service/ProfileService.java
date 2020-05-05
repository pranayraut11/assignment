package com.amdocs.media.assignement.authorization.service;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;

public interface ProfileService {

	public Mono<ProfileDTO> save(ProfileDTO profile);

	public Mono<Disposable> update(ProfileDTO profile) throws NotFoundException, JsonProcessingException;

	public Mono<Disposable> delete(int profileId) throws JsonProcessingException;

}
