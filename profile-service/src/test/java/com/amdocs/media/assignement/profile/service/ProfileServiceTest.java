package com.amdocs.media.assignement.profile.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.amdocs.media.assignement.profile.entity.Profile;
import com.amdocs.media.assignement.profile.exception.NotFoundException;
import com.amdocs.media.assignement.profile.repository.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

	@InjectMocks
	private ProfileServiceImpl profileService;

	@Mock
	private ProfileServiceImpl profileServiceMock;

	@Mock
	private ProfileRepository profileRepository;

	@Test
	public void saveProfileTest() {

		Profile profile = new Profile();
		profile.setId(1);
		profile.setAddress("104");
		profile.setMobile("123213123");
		profile.setUserId(1);

		when(profileRepository.save(profile)).thenReturn(profile);

		Mono<Profile> profileMono = profileService.save(profile);
		StepVerifier.create(profileMono).consumeNextWith((response) -> {
			assertEquals(1, response.getId());
		});

	}

	@Test
	public void updateProfileTest() throws JsonMappingException, NotFoundException, JsonProcessingException {

		Profile profile = new Profile();
		profile.setId(1);
		profile.setAddress("104");
		profile.setMobile("123213123");
		profile.setUserId(1);

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setUserId(1);
		profileDTO.setAddress("105");
		profileDTO.setMobile("8786876");

		when(profileRepository.findByUserId(1)).thenReturn(Optional.of(profile));
		when(profileRepository.save(profile)).thenReturn(profile);

		profileService.update(profileDTO);
		verify(profileRepository, times(1)).save(profile);

	}

	@Test(expected = NotFoundException.class)
	public void updateProfileNotFoundExceptionTest()
			throws JsonMappingException, NotFoundException, JsonProcessingException {

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setUserId(1);
		profileDTO.setAddress("105");
		profileDTO.setMobile("8786876");
		profileService.update(profileDTO);

	}

	@Test
	public void deleteProfileTest() throws JsonMappingException, NotFoundException, JsonProcessingException {

		Profile profile = new Profile();
		profile.setId(1);
		profile.setAddress("104");
		profile.setMobile("123213123");
		profile.setUserId(1);

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setUserId(1);
		profileDTO.setAddress("105");
		profileDTO.setMobile("8786876");
		when(profileRepository.findByUserId(1)).thenReturn(Optional.of(profile));
		profileService.delete(profileDTO);
		verify(profileRepository, times(1)).delete(profile);

	}

	@Test(expected = NotFoundException.class)
	public void deleteProfileNotFoundExceptionTest()
			throws JsonMappingException, NotFoundException, JsonProcessingException {

		Profile profile = new Profile();
		profile.setId(1);
		profile.setAddress("104");
		profile.setMobile("123213123");
		profile.setUserId(1);

		ProfileDTO profileDTO = new ProfileDTO();
		profileDTO.setUserId(1);
		profileDTO.setAddress("105");
		profileDTO.setMobile("8786876");
		profileService.delete(profileDTO);
		verify(profileRepository, times(1)).delete(profile);

	}

}
