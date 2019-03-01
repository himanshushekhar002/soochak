package com.notificationlab.soochak.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notificationlab.soochak.constants.KafkaConstant;
import com.notificationlab.soochak.dto.IMessage;
import com.notificationlab.soochak.dto.Message;

@RestController
public class MessagePushController {

	private final MessagePushService messagePushService;
	
	
	@Autowired
	public MessagePushController(MessagePushService messagePushService) {
		this.messagePushService = messagePushService;
	}
	
	@PostMapping(value="/message/push",consumes=MediaType.APPLICATION_JSON_VALUE)
	IMessage push(@RequestBody Message message) throws JsonProcessingException {
		return messagePushService.push(KafkaConstant.TOPIC_NAME_MESSAGE, message);
	}
}
