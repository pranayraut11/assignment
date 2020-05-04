package com.amdocs.media.assignement.profile.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.amdocs.media.assignement.profile.entity.Profile;
import com.amdocs.media.assignement.profile.exception.NotFoundException;
import com.amdocs.media.assignement.profile.repository.ProfileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Service
public class ProfileServiceImpl implements ProfileService {

	private static final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class.getName());

	private KafkaReceiver<Object, Object> kafkaReceiver;

	@Autowired
	private ProfileRepository profileRepository;

	@Value("${broker.url}")
	private String brokerUrl;

	@Value("${topic.update}")
	private String updateTopic;

	public ProfileServiceImpl() {

		final Map<String, Object> consumerProps = new HashMap<>();
		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumerProps.put(ConsumerConfig.CLIENT_ID_CONFIG, "client");
		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "client-group");
		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerUrl);

		ReceiverOptions<Object, Object> consumerOptions = ReceiverOptions.create(consumerProps)
				.subscription(Collections.singleton(updateTopic))
				.addAssignListener(partitions -> log.debug("onPartitionsAssigned {}", partitions))
				.addRevokeListener(partitions -> log.debug("onPartitionsRevoked {}", partitions));

		kafkaReceiver = KafkaReceiver.create(consumerOptions);
		Flux<ReceiverRecord<Object, Object>> flux = kafkaReceiver.receive();

		flux.doOnNext(r -> {
			System.out.println("Messaage" + r.value());

			r.receiverOffset().acknowledge();
		}).subscribe();
	}

	@Override
	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

	@Override
	public void update(String profile) throws NotFoundException, JsonMappingException, JsonProcessingException {
		ObjectMapper objectmapper = new ObjectMapper();
		ModelMapper mapper = new ModelMapper();
		ProfileDTO profileDTO = objectmapper.readValue(profile, ProfileDTO.class);
		Profile profileToUpdate = getProfile(profileDTO.getUserId());
		mapper.map(profileDTO, profileToUpdate);
		profileRepository.save(profileToUpdate);
	}

	@Override
	public void delete(int userId) {
		profileRepository.delete(getProfile(userId));
	}

	private Profile getProfile(Integer userId) {
		Optional<Profile> profileReponse = profileRepository.findByUserId(userId);
		return profileReponse.orElseThrow(() -> new NotFoundException("Profile"));

	}

}
