package com.amdocs.media.assignement.authorization;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.amdocs.media.assignement.authorization.service.ProfileService;
import com.amdocs.media.assignement.authorization.service.ProfileServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
@Ignore
class AuthorizationServiceApplicationTests {

	@Autowired
	private ProfileService profileService = new ProfileServiceImpl();
	@Test
	public void updateProfile() throws NotFoundException, JsonProcessingException {
		ProfileDTO profile = new ProfileDTO();
		profile.setMobile("87665463333");
		profile.setAddress("A-1");
		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
		profileService.update(profile);
	}

}
