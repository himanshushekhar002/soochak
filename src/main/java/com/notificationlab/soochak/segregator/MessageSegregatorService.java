package com.notificationlab.soochak.segregator;

import java.io.IOException;
import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationlab.soochak.constants.KafkaConstant;
import com.notificationlab.soochak.constants.MessageType;
import com.notificationlab.soochak.dto.IMessage;
import com.notificationlab.soochak.dto.Message;
import com.notificationlab.soochak.event.repository.Event;
import com.notificationlab.soochak.event.repository.EventRepository;
import com.notificationlab.soochak.event.status.EventStatus;
import com.notificationlab.soochak.event.status.EventStatusService;
import com.notificationlab.soochak.push.MessagePushService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageSegregatorService {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private final MessagePushService messagePushService;
	private final EventRepository eventRepository;
	private final EventStatusService statusService;
	
	MessageSegregatorService(MessagePushService messagePushService, EventRepository eventRepository, EventStatusService statusService){
		this.messagePushService = messagePushService;
		this.eventRepository = eventRepository;
		this.statusService = statusService;
	}
	
	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_MESSAGE, groupId = KafkaConstant.GROUPID)
	public void listenMessageQueue(String message) throws IOException {
			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_MESSAGE + " : " + message);
			Message msgObject = mapper.readValue(message, Message.class);
			processMessage(msgObject);
	}
	
	private void processMessage(IMessage message) throws JsonProcessingException {
		log.info("-------------------------Message payload received for processing with id:: "+message.getId()+ " for event :: "+message.getEventId()+"-------------------------");
		statusService.createStatus(message.getEventId());
		//MESSAGE SEGREGATOR HERE
	    segregate(message);
	    log.info("************************Message Processing done***************************");
	}

	
	private void segregate(IMessage message) throws JsonProcessingException {
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
}
