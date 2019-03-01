package com.notificationlab.soochak.dto;

import java.util.Map;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements IMessage {

	private final String id=UUID.randomUUID().toString();
	
	private long eventId;
	private Map<String, Object> metaData;
	private Map<String, Object> data;
	
	public Message(long eventId){
		this(eventId, null, null);
	}
}
