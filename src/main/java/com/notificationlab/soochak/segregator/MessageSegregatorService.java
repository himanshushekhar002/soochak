package com.notificationlab.soochak.segregator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notificationlab.soochak.constants.KafkaConstant;
import com.notificationlab.soochak.dto.IMessage;
import com.notificationlab.soochak.email.Email;
import com.notificationlab.soochak.event.repository.Event;
import com.notificationlab.soochak.event.repository.EventRepository;
import com.notificationlab.soochak.push.MessagePushService;

@Service
public class MessageSegregatorService {
	
	private final MessagePushService messagePushService;
	private final EventRepository eventRepository;
	
	MessageSegregatorService(MessagePushService messagePushService, EventRepository eventRepository){
		this.messagePushService = messagePushService;
		this.eventRepository = eventRepository;
	}
	
	public void segregate(IMessage message) throws JsonProcessingException {
		long eventId = message.getEventId();
		Optional<Event> optionalEvent = eventRepository.findById(eventId);
		if(optionalEvent.isPresent()) {
			Event event = optionalEvent.get();
			if(event.isEmailEnabled()) {
				messagePushService.push(KafkaConstant.TOPIC_NAME_EMAIL_MESSSAGE, message);
			}
			if(event.isSmsEnabled()) {
				messagePushService.push(KafkaConstant.TOPIC_NAME_SMS_MESSAGE, message);
			}
			if(event.isPnEnabled()) {
				messagePushService.push(KafkaConstant.TOPIC_NAME_PN_MESSAGE, message);
			}
		}		
	}
	
	/*
	 * This method can be used to distribute events
	 * in priority Qs or respective Organization Qs.
	 * Currently it simply pushes to single Q  
	 */
	public void segregate(Email email) throws JsonProcessingException {
		messagePushService.push(KafkaConstant.TOPIC_NAME_EMAIL_COMPOSED, email);
	}
}
