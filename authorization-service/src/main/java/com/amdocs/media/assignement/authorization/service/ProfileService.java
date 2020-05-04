package com.amdocs.media.assignement.authorization.service;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface ProfileService {

	public void save(ProfileDTO profile);

	public void update(ProfileDTO profile) throws NotFoundException, JsonProcessingException;

	public void delete(int profileId);

}
