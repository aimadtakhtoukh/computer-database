package com.excilys.core.serialization;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
/**
 * Automatically deserializes LocalDateTime objects from dates written in the ISO
 * standard. LocalDateTime support doesn't exist yet.
 * @author excilys
 *
 */
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime>  {

	@Override
	public LocalDateTime deserialize(JsonParser parser,
			DeserializationContext arg1) throws IOException,
			JsonProcessingException {
		return LocalDateTime.parse(parser.getText(), DateTimeFormatter.ISO_DATE_TIME);
		
	}
}
