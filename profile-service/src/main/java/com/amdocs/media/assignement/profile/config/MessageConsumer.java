package com.amdocs.media.assignement.profile.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.amdocs.media.assignement.profile.service.ProfileService;
import com.amdocs.media.assignement.profile.utils.ObjectConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Configuration
public class MessageConsumer {

	private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class.getName());

	private KafkaReceiver<Object, Object> kafkaReceiver;

	@Value("${broker.url}")
	String brokerUrl;

	@Value("${topic.update}")
	String updateTopic;

	@Autowired
	private ProfileService profileService;

	@Bean
	public void createMessageConsumer() {
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
			log.info("Message Consumed {}", r.value().toString());
			try {
				execute(r.value().toString());
			} catch (JsonProcessingException e) {
				log.error("Error occoured while processing Json{}", e.getLocalizedMessage());
			}
			r.receiverOffset().acknowledge();
			log.debug("acknowledgement sent successfully at time {} ", r.timestamp());
		}).doOnError(e -> {
			log.error("Error while conssuming message {} ", e);
		}).subscribe();

	}

	private void execute(String messageJson) throws JsonMappingException, JsonProcessingException {
		Object message = ObjectConverter.getObject(messageJson);

		switch (ObjectConverter.getAction(messageJson)) {
		case "updateProfile":
			log.debug("Executing update profile ");
			ProfileDTO updateProfileDTO = (ProfileDTO) message;
			profileService.update(updateProfileDTO);
			log.debug("update profile executed successfully");
			break;
		case "deleteProfile":
			log.debug("Executing delete profile ");
			ProfileDTO deleteProfileDTO = (ProfileDTO) message;
			profileService.delete(deleteProfileDTO);
			log.debug("Delete profile executed successfully");
			break;

		default:
			break;
		}
	}

}
