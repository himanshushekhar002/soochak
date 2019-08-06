package com.notificationlab.soochak.pull;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationlab.soochak.constants.KafkaConstant;
import com.notificationlab.soochak.dto.Message;
import com.notificationlab.soochak.email.Email;
import com.notificationlab.soochak.pn.Pn;
import com.notificationlab.soochak.sms.Sms;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessagePullService {

	private final ObjectMapper mapper = new ObjectMapper();

	private final MessageProcessingService messageProcessingService;

	public MessagePullService(MessageProcessingService messageProcessingService) {
		this.messageProcessingService = messageProcessingService;
	}

//	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_MESSAGE, groupId = KafkaConstant.GROUPID)
//	public void listenMessageQueue(String message) throws IOException {
//			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_MESSAGE + " : " + message);
//			Message msgObject = mapper.readValue(message, Message.class);
//			messageProcessingService.processMessage(msgObject);
//	}

//	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_EMAIL_MESSSAGE, groupId = KafkaConstant.GROUPID)
//	public void listenEmailMessageQueue(String message) throws IOException, MessagingException {
//			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_EMAIL_MESSSAGE + " : " + message);
//			Message msgObject = mapper.readValue(message, Message.class);
//			messageProcessingService.composeEmail(msgObject);
//	}

//	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_EMAIL_COMPOSED, groupId = KafkaConstant.GROUPID)
//	public void listenEmailComposedQueue(String message) throws IOException, MessagingException {
//			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_EMAIL_COMPOSED + " : " + message);
//			Email email = mapper.readValue(message, Email.class);
//			messageProcessingService.processEmail(email);
//	}

	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_SMS_MESSAGE, groupId = KafkaConstant.GROUPID)
	public void listenSMSMessageQueue(String message) throws IOException {
			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_SMS_MESSAGE + " : " + message);
			Message msgObject = mapper.readValue(message, Message.class);
			messageProcessingService.composeSMS(msgObject);
	}

	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_SMS_COMPOSED, groupId = KafkaConstant.GROUPID)
	public void listenSMSComposedQueue(String message) throws IOException {
			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_SMS_COMPOSED + " : " + message);
			Sms smsObject = mapper.readValue(message, Sms.class);
			messageProcessingService.processSMS(smsObject);
	}

	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_PN_MESSAGE, groupId = KafkaConstant.GROUPID)
	public void listenPNMessageQueue(String message) throws IOException {
			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_PN_MESSAGE + " : " + message);
			Message msgObject = mapper.readValue(message, Message.class);
			messageProcessingService.composePN(msgObject);
	}

	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_PN_COMPOSED, groupId = KafkaConstant.GROUPID)
	public void listenPNComposedQueue(String message) throws IOException {
			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_PN_COMPOSED + " : " + message);
			Pn pnObject = mapper.readValue(message, Pn.class);
			messageProcessingService.processPN(pnObject);
	}
}
