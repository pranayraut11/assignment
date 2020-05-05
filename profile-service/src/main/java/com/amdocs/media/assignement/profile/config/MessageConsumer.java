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
import org.springframework.stereotype.Component;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.amdocs.media.assignement.profile.service.ProfileService;
import com.amdocs.media.assignement.profile.utils.ObjectConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Component
public class MessageConsumer {

	private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class.getName());

	private KafkaReceiver<Object, Object> kafkaReceiver;

	@Value("${broker.url}")
	private String brokerUrl;

	@Value("${topic.update}")
	private String updateTopic;

	@Autowired
	private ProfileService profileService;

	public MessageConsumer() {
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
			try {
				execute(r.value().toString());
			} catch (JsonProcessingException e) {
				// TO-DO Add error to error topic

				e.printStackTrace();
			}
			r.receiverOffset().acknowledge();
		}).subscribe();

	}

	private void execute(String messageJson) throws JsonMappingException, JsonProcessingException {
		Object message = ObjectConverter.getObject(messageJson);

		switch (ObjectConverter.getAction(messageJson)) {
		case "updateProfile":
			ProfileDTO updateProfileDTO = (ProfileDTO) message;
			profileService.update(updateProfileDTO);
			break;
		case "deletProfile":
			ProfileDTO deleteProfileDTO = (ProfileDTO) message;
			profileService.delete(deleteProfileDTO);
			break;

		default:
			break;
		}
	}

}
