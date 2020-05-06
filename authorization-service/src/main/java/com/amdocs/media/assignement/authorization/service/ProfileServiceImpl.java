package com.amdocs.media.assignement.authorization.service;

import org.apache.kafka.clients.producer.ProducerRecord;
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
		return webClient.build().post().uri("/profile").body(Mono.just(profile), ProfileDTO.class).retrieve()
				.bodyToMono(ProfileDTO.class);
	}

	@Override
	public void update(ProfileDTO profile) throws NotFoundException, JsonProcessingException {
		Message<ProfileDTO> profileDTO = new Message<>();
		profileDTO.setAction("updateProfile");
		profileDTO.setDtoName(ProfileDTO.class.getName());
		profileDTO.setMessage(profile);
		sendMessage(updateQueue, profileDTO);

	}

	@Override
	public void delete(int profileId) throws JsonProcessingException {
		ProfileDTO profile = new ProfileDTO();
		profile.setUserId(profileId);
		Message<ProfileDTO> profileDTO = new Message<>();
		profileDTO.setAction("updateProfile");
		profileDTO.setDtoName(ProfileDTO.class.getName());
		profileDTO.setMessage(profile);
		sendMessage(updateQueue, profileDTO);
	}

	private <T> Disposable sendMessage(String topic, Message<T> message) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		Flux<SenderRecord<Integer, String, Integer>> outFlux = Flux
				.just(SenderRecord.create(new ProducerRecord<>(topic, 0, mapper.writeValueAsString(message)), 0));
		return sender.send(outFlux).doOnError(e -> System.out.println(e))
				.doOnNext(n -> System.out.println(
						"correlation Metadate : " + n.correlationMetadata() + " Metadata : " + n.recordMetadata()))
				.subscribe();
	}
}
