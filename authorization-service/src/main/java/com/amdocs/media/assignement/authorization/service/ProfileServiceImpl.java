package com.amdocs.media.assignement.authorization.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.amdocs.media.assignement.authorization.dto.Message;
import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class ProfileServiceImpl implements ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

	@Autowired
	private WebClient.Builder webClient;

	@Autowired
	private KafkaSender<Integer, String> sender;

	@Value("${topic.update}")
	String updateQueue;

	@Value("${profile.save.url}")
	private String saveProfileUrl;

	@Override
	public Mono<ProfileDTO> save(ProfileDTO profile) {
		log.info("creating user profile");
		return webClient.build().post().uri("/profile").body(Mono.just(profile), ProfileDTO.class).retrieve()
				.bodyToMono(ProfileDTO.class);
	}

	@Override
	public void update(ProfileDTO profile) throws NotFoundException, JsonProcessingException {
		log.info("Updating user profile with user Id {}", profile.getUserId());
		Message<ProfileDTO> profileDTO = new Message<>();
		profileDTO.setAction("updateProfile");
		profileDTO.setDtoName(ProfileDTO.class.getSimpleName());
		profileDTO.setMessage(profile);
		sendMessage(updateQueue, profileDTO);
		log.info("Message successfully sent to update user profile with user Id {}", profile.getUserId());
	}

	@Override
	public void delete(int profileId) throws JsonProcessingException {
		log.info("Deleting user profile with user Id {}", profileId);
		ProfileDTO profile = new ProfileDTO();
		profile.setUserId(profileId);
		Message<ProfileDTO> profileDTO = new Message<>();
		profileDTO.setAction("deleteProfile");
		profileDTO.setDtoName(ProfileDTO.class.getSimpleName());
		profileDTO.setMessage(profile);
		sendMessage(updateQueue, profileDTO);
		log.info("Message successfully sent to delete user profile with user Id {}", profileId);
	}

	private <T> Disposable sendMessage(String topic, Message<T> message) throws JsonProcessingException {
		log.debug("Sending message to {} topic", topic);
		ObjectMapper mapper = new ObjectMapper();
		Flux<SenderRecord<Integer, String, Integer>> outFlux = Flux
				.just(SenderRecord.create(new ProducerRecord<>(topic, 0, mapper.writeValueAsString(message)), 0));
		return sender.send(outFlux).doOnError(e -> log.error(e.getLocalizedMessage()))
				.doOnNext(n -> log.debug("Message send successfully : Metadata : {}", n.recordMetadata().timestamp()))
				.subscribe();
	}
}
