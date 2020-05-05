package com.amdocs.media.assignement.profile.utils;

import com.amdocs.media.assignement.profile.dto.ProfileDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ObjectConverter {

	private static ObjectMapper objectmapper = null;

	private static final String ACTION = "action";

	private static final String MESSAGE = "message";

	private static final String DTO_NAME = "dtoName";
	static {
		objectmapper = new ObjectMapper();

	}

	public static Object getObject(String json) throws JsonMappingException, JsonProcessingException {
		ObjectNode objectNode = objectmapper.readValue(json, ObjectNode.class);
		if (objectNode.hasNonNull(DTO_NAME)) {
			return convert(objectNode.get(DTO_NAME).asText(), objectNode.get(MESSAGE).asText());
		}
		return null;

	}

	private static Object convert(String action, String objectJson)
			throws JsonMappingException, JsonProcessingException {
		switch (action) {
		case "profileDTO":
			return objectmapper.readValue(objectJson, ProfileDTO.class);

		default:
			break;
		}
		return null;
	}

	public static String getAction(String json) throws JsonMappingException, JsonProcessingException {
		ObjectNode objectNode = objectmapper.readValue(json, ObjectNode.class);
		if (objectNode.hasNonNull(ACTION)) {
			return objectNode.get(ACTION).asText();
		}
		return null;

	}

}
