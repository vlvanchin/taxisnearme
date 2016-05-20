package com.hackathon.hitaxi.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    protected static final Log logger = LogFactory.getLog(JsonUtils.class);

    private final static ObjectMapper mapper = new ObjectMapper();

    public static final MediaType JSON_MEDIA_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("UTF-8"));

    public static Map<String, Object> convertJsonToMap(String json) {
	Map<String, Object> retMap = new HashMap<String, Object>();
	if (json != null) {
	    try {
		retMap = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
		});
	    } catch (IOException e) {
		logger.warn("Error while reading Java Map from JSON response: " + json, e);
	    }
	}
	return retMap;
    }

    public static List<String> convertJsonToList(String json) {
	List<String> retList = new ArrayList<String>();
	if (json != null) {
	    try {
		retList = mapper.readValue(json, new TypeReference<List<String>>() {
		});
	    } catch (IOException e) {
		logger.warn("Error while reading Java List from JSON response: " + json, e);
	    }
	}
	return retList;
    }

    public static String convertToJson(Object value) {
	if (mapper.canSerialize(value.getClass())) {
	    try {
		return mapper.writeValueAsString(value);
	    } catch (IOException e) {
		logger.warn("Error while serializing " + value + " to JSON", e);
		return null;
	    }
	} else {
	    throw new IllegalArgumentException(
	            "Value of type " + value.getClass().getName() + " can not be serialized to JSON.");
	}
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> convertToJsonList(InputStream jsonInputStream) {
	try {
	    return mapper.readValue(jsonInputStream, List.class);
	} catch (JsonParseException e) {
	    logger.error("Unable to parse JSON from InputStream", e);
	    throw new IllegalArgumentException("Unable to parse JSON from InputStream", e);
	} catch (JsonMappingException e) {
	    logger.error("Unable to parse JSON from InputStream", e);
	    throw new IllegalArgumentException("Unable to parse JSON from InputStream", e);
	} catch (IOException e) {
	    logger.error("Unable to process InputStream", e);
	    throw new IllegalArgumentException("Unable to parse JSON from InputStream", e);
	}
    }
}
