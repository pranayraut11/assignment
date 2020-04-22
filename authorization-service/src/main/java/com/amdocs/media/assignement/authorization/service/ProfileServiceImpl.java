package com.amdocs.media.assignement.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignement.authorization.client.ProfileClient;
import com.amdocs.media.assignement.authorization.dto.ProfileDTO;
import com.amdocs.media.assignement.authorization.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileClient profileClient;

	@Value("${activemq.updateQueue}")
	String updateQueue;

	@Value("${activemq.deleteQueue}")
	String deletQueue;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void save(ProfileDTO profile) {
		profileClient.createProfile(profile);
	}

	@Override
	public void update(ProfileDTO profile) throws NotFoundException, JmsException, JsonProcessingException {
		System.out.println("Sending message to update profile" + profile);
		ObjectMapper mapper = new ObjectMapper();
		jmsTemplate.convertAndSend(updateQueue, mapper.writeValueAsString(profile));
		System.out.println("Successfully send profile message");
	}

	@Override
	public void delete(int profileId) {
		jmsTemplate.convertAndSend(deletQueue, profileId);
	}

}
