package com.amdocs.media.assignement.profile.service;

import java.util.Optional;

import org.modelmapper.Conditions;
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

import reactor.core.publisher.Mono;

@Service
public class ProfileServiceImpl implements ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class.getName());

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Mono<Profile> save(Profile profile) {
		return Mono.just(profileRepository.save(profile));
	}

	@Override
	public void update(ProfileDTO profileDTO) throws NotFoundException, JsonMappingException, JsonProcessingException {
		log.info("Updating User Profile");
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		Profile profileToUpdate = getProfile(profileDTO.getUserId());
		mapper.map(profileDTO, profileToUpdate);
		profileRepository.save(profileToUpdate);
		log.info("User Profile Updated successfully");
	}

	@Override
	public void delete(ProfileDTO userId) {
		log.info("Deleting User Profile");
		profileRepository.delete(getProfile(userId.getUserId()));
		log.info("Deleted User Profile");
	}

	public Profile getProfile(Integer userId) {
		log.info("Retrieving User Profile by user id {}", userId);
		Optional<Profile> profileReponse = profileRepository.findByUserId(userId);
		return profileReponse.orElseThrow(() -> new NotFoundException("Profile"));

	}

}
