package com.amdocs.media.assignement.profile.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.amdocs.media.assignement.profile.entity.Profile;
import com.amdocs.media.assignement.profile.exception.NotFoundException;
import com.amdocs.media.assignement.profile.repository.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class ProfileServiceImpl implements ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class.getName());

	@Autowired
	private ProfileRepository profileRepository;

	public ProfileServiceImpl() {

	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public void update(ProfileDTO profileDTO) throws NotFoundException, JsonMappingException, JsonProcessingException {
		ModelMapper mapper = new ModelMapper();
		Profile profileToUpdate = getProfile(profileDTO.getUserId());
		mapper.map(profileDTO, profileToUpdate);
		profileRepository.save(profileToUpdate);
	}

	@Override
	public void delete(ProfileDTO userId) {
		profileRepository.delete(getProfile(userId.getUserId()));
	}

	private Profile getProfile(Integer userId) {
		Optional<Profile> profileReponse = profileRepository.findByUserId(userId);
		return profileReponse.orElseThrow(() -> new NotFoundException("Profile"));

	}

}
