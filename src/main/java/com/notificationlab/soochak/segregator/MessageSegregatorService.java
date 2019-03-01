package com.notificationlab.soochak.segregator;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notificationlab.soochak.constants.KafkaConstant;
import com.notificationlab.soochak.constants.MessageType;
import com.notificationlab.soochak.dto.IMessage;
import com.notificationlab.soochak.email.Email;
import com.notificationlab.soochak.event.repository.Event;
import com.notificationlab.soochak.event.repository.EventRepository;
import com.notificationlab.soochak.event.status.EventStatus;
import com.notificationlab.soochak.event.status.EventStatusService;
import com.notificationlab.soochak.push.MessagePushService;

@Service
public class MessageSegregatorService {
	
	private final MessagePushService messagePushService;
	private final EventRepository eventRepository;
	private final EventStatusService statusService;
	
	MessageSegregatorService(MessagePushService messagePushService, EventRepository eventRepository, EventStatusService statusService){
		this.messagePushService = messagePushService;
		this.eventRepository = eventRepository;
		this.statusService = statusService;
	}
	
	public void segregate(IMessage message) throws JsonProcessingException {
		long eventId = message.getEventId();
		Optional<Event> optionalEvent = eventRepository.findById(eventId);
		if(optionalEvent.isPresent()) {
			Event event = optionalEvent.get();
			if(event.isEmailEnabled()) {
				messagePushService.push(KafkaConstant.TOPIC_NAME_EMAIL_MESSSAGE, message);
			}else {
				statusService.updateStatus(message.getEventId(), EventStatus.Status.NOTAPPLICABLE, MessageType.EMAIL);
			}
			if(event.isSmsEnabled()) {
				messagePushService.push(KafkaConstant.TOPIC_NAME_SMS_MESSAGE, message);
			}else {
				statusService.updateStatus(message.getEventId(), EventStatus.Status.NOTAPPLICABLE, MessageType.SMS);
			}
			if(event.isPnEnabled()) {
				messagePushService.push(KafkaConstant.TOPIC_NAME_PN_MESSAGE, message);
			}else {
				statusService.updateStatus(message.getEventId(), EventStatus.Status.NOTAPPLICABLE, MessageType.PN);
			}
		}		
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
