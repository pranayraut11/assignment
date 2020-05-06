package com.amdocs.media.assignement.profile.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.amdocs.media.assignement.profile.entity.Profile;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class ProfileControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void saveProfileCountTest() {
		Profile profileDTO = new Profile();

		profileDTO.setUserId(1);
		profileDTO.setAddress("104");
		profileDTO.setMobile("123213213");

		Flux<Profile> flux = webTestClient.post().uri("/profile").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(profileDTO), Profile.class).exchange()
				.returnResult(Profile.class).getResponseBody();

		StepVerifier.create(flux).expectNextCount(1).verifyComplete();
	}

	@Test
	public void saveProfileResultTest() {
		Profile profileDTO = new Profile();

		profileDTO.setUserId(1);
		profileDTO.setAddress("104");
		profileDTO.setMobile("123213213");
		webTestClient.post().uri("/profile").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(profileDTO), Profile.class).exchange().returnResult(Profile.class)
				.consumeWith((response) -> {
					assertEquals(response.getResponseBody().blockFirst().getAddress(), "104");
				});

	}

}
