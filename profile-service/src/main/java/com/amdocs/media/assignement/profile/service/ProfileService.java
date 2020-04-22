package com.amdocs.media.assignement.profile.service;

import com.amdocs.media.assignement.profile.entity.Profile;
import com.amdocs.media.assignement.profile.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ProfileService {
	public Profile save(Profile profile);

	public void update(String profile) throws  NotFoundException, JsonMappingException, JsonProcessingException ;

	public void delete(int profileId);
}
