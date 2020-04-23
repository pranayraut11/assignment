package com.amdocs.media.assignement.profile.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.amdocs.media.assignement.profile.entity.Profile;
import com.amdocs.media.assignement.profile.exception.NotFoundException;
import com.amdocs.media.assignement.profile.repository.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@EnableJms
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@JmsListener(destination = "${activemq.updateQueue}")
	@Override
	public void update(String profile) throws NotFoundException, JsonMappingException, JsonProcessingException {
		ObjectMapper objectmapper = new ObjectMapper();
		ModelMapper mapper = new ModelMapper();
		ProfileDTO profileDTO = objectmapper.readValue(profile, ProfileDTO.class);
		Profile profileToUpdate = getProfile(profileDTO.getUserId());
		mapper.map(profileDTO, profileToUpdate);
		profileRepository.save(profileToUpdate);
	}

	@JmsListener(destination = "${activemq.deleteQueue}")
	@Override
	public void delete(int userId) {
		profileRepository.delete(getProfile(userId));
	}

	private Profile getProfile(Integer userId) {
		Optional<Profile> profileReponse = profileRepository.findByUserId(userId);
		return profileReponse.orElseThrow(() -> new NotFoundException("Profile"));

	}
}
