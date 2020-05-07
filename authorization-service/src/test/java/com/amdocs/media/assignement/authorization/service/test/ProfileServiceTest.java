package com.amdocs.media.assignement.authorization.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amdocs.media.assignement.authorization.controller.ProfileController;
import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.amdocs.media.assignement.authorization.service.ProfileServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import reactor.core.publisher.Mono;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

	@Mock
	private ProfileServiceImpl profileService;

	@InjectMocks
	private ProfileController profileController;

	@Test
	public void saveProfileTest() {
		ProfileDTO dto = new ProfileDTO();
		dto.setId(1);
		dto.setUserId(1);
		dto.setMobile("123123123");
		dto.setAddress("104");
		Mono<ProfileDTO> profileMono = Mono.just(dto);
		when(profileService.save(dto)).thenReturn(profileMono);
		ProfileDTO profileMonoResponse = profileController.createProfile(dto).block();
		assertEquals(dto.getId(), profileMonoResponse.getId());

	}

	@Test
	public void updateProfileTest() throws NotFoundException, JsonProcessingException {
		ProfileDTO dto = new ProfileDTO();
		dto.setId(1);
		dto.setUserId(1);
		dto.setMobile("123123123");
		dto.setAddress("104");

		profileController.updateProfile(dto);
		verify(profileService, times(1)).update(dto);

	}

	@Test
	public void deleteProfileTest() throws NotFoundException, JsonProcessingException {

		profileController.deletProfile(1);

		verify(profileService, times(1)).delete(1);
	}
}
