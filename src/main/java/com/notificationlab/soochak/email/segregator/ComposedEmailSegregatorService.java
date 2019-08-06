package com.notificationlab.soochak.email.segregator;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notificationlab.soochak.constants.KafkaConstant;
import com.notificationlab.soochak.email.Email;
import com.notificationlab.soochak.push.MessagePushService;

@Service
public class ComposedEmailSegregatorService {

	private final MessagePushService messagePushService;
	
	ComposedEmailSegregatorService(MessagePushService messagePushService){
		this.messagePushService = messagePushService;
	}
	
	/*
	 * This method can be used to distribute events
	 * in priority Qs or respective Organization Qs or retry Qs.
	 * Currently it simply pushes to single Qs
	 */
	public void segregate(Email email) throws JsonProcessingException {
		messagePushService.push(KafkaConstant.TOPIC_NAME_EMAIL_COMPOSED, email);
	}
}
