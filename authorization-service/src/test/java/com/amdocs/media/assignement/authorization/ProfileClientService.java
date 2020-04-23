package com.amdocs.media.assignement.authorization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.amdocs.media.assignement.authorization.client.ProfileClient;
import com.amdocs.media.assignement.authorization.dto.ProfileDTO;

public class ProfileClientService implements ProfileClient {

	@Override
	public ResponseEntity<Void> createProfile(ProfileDTO profile) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
