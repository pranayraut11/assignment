package com.amdocs.media.assignement.authorization.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amdocs.media.assignement.authorization.dto.ProfileDTO;

@FeignClient(name = "profile-service")
public interface ProfileClient {

	@PostMapping("profile")
	public ResponseEntity<Void> createProfile(@RequestBody ProfileDTO profile);
}
