package com.amdocs.media.assignement.authorization.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.authorization.client.ProfileClient;
import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileClient profileClient;

	@Autowired
	private KafkaSender<Integer, String> sender;

	@Value("${activemq.updateQueue}")
	String updateQueue;

	@Value("${activemq.deleteQueue}")
	String deletQueue;

	@Override
	public void save(ProfileDTO profile) {
		profileClient.createProfile(profile);
	}

	@Override
	public void update(ProfileDTO profile) throws NotFoundException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Flux<SenderRecord<Integer, String, Integer>> outFlux = Flux
				.just(SenderRecord.create(new ProducerRecord<>(updateQueue, 0, mapper.writeValueAsString(profile)), 0));
		sender.send(outFlux).doOnError(e -> System.out.println(e))
				.doOnNext(n -> System.out.println(
						"correlation Metadate : " + n.correlationMetadata() + " Metadata : " + n.recordMetadata()))
				.subscribe();
	}

	@Override
	public void delete(int profileId) {
		// jmsTemplate.convertAndSend(deletQueue, profileId);
	}

}
