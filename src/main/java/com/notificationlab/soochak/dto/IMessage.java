package com.notificationlab.soochak.dto;

import java.util.Map;

public interface IMessage {

	String getId();
	long getEventId();
	Map<String, Object> getData();
}
