package com.amdocs.media.assignement.authorization.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "profile-service")
public interface ProfileClient {

	@PostMapping("profile")
	public ResponseEntity<Void> createProfile(@RequestBody ProfileDTO profile);
}

@Component
class ProfileClientFallbackService implements FallbackFactory<ProfileClient> {

	@Override
	public ProfileClient create(Throwable cause) {
		return new ProfileClientService(cause);
	}

}

class ProfileClientService implements ProfileClient {

	private final Throwable cause;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	ProfileClientService(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public ResponseEntity<Void> createProfile(ProfileDTO profile) {
		if ((cause instanceof FeignException) && ((FeignException) cause).status() == 404) {
			logger.error(cause.getLocalizedMessage());
		} else {
			logger.error(cause.getLocalizedMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
