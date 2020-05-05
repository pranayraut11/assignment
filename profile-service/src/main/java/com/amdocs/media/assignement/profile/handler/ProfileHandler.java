package com.amdocs.media.assignement.profile.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.amdocs.media.assignement.profile.entity.Profile;
import com.amdocs.media.assignement.profile.service.ProfileService;

import reactor.core.publisher.Mono;

@Component
public class ProfileHandler {

	@Autowired
	private ProfileService profileService;

	public Mono<ServerResponse> saveProfile(ServerRequest request) {
		Mono<Profile> profileMono = request.bodyToMono(Profile.class);
		return profileMono.flatMap(profile -> ServerResponse.status(HttpStatus.CREATED)
				.contentType(MediaType.APPLICATION_JSON).body(profileService.save(profile), Profile.class));

	}
}