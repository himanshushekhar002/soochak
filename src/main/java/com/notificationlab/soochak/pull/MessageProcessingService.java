package com.notificationlab.soochak.pull;

import java.net.MalformedURLException;

import javax.mail.MessagingException;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.notificationlab.soochak.constants.MessageType;
import com.notificationlab.soochak.dto.IMessage;
import com.notificationlab.soochak.email.Email;
import com.notificationlab.soochak.email.composer.MailComposerService;
import com.notificationlab.soochak.email.processor.MailProcessingService;
import com.notificationlab.soochak.event.status.EventStatus;
import com.notificationlab.soochak.event.status.EventStatusService;
import com.notificationlab.soochak.pn.Pn;
import com.notificationlab.soochak.segregator.MessageSegregatorService;
import com.notificationlab.soochak.sms.Sms;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageProcessingService {

	private final ApplicationContext  applicationContext;
	
	private final EventStatusService statusService;
	
	public MessageProcessingService(ApplicationContext  applicationContext) {
		
		this.applicationContext = applicationContext;
		this.statusService = applicationContext.getBean(EventStatusService.class);
	}
	
	public void processMessage(IMessage message) throws JsonProcessingException {
		log.info("-------------------------Message payload received for processing with id:: "+message.getId()+ " for event :: "+message.getEventId()+"-------------------------");
		statusService.createStatus(message.getEventId());
		//MESSAGE SEGREGATOR HERE
		MessageSegregatorService service = applicationContext.getBean(MessageSegregatorService.class);
	    service.segregate(message);
	    log.info("************************Message Processing done***************************");
	}
	
	

	public void composeEmail(IMessage message) throws MessagingException, MalformedURLException, JsonProcessingException {
		log.info("-------------------------Message payload received for composing email with id:: "+message.getId()+ " for event :: "+message.getEventId()+"-------------------------");
		statusService.updateStatus(message.getEventId(), EventStatus.Status.PROCESSING, MessageType.EMAIL);
		//MAIL COMPOSER SERVICE
		MailComposerService composerservice = applicationContext.getBean(MailComposerService.class);
		Email email = composerservice.composeEmail(message);
		MessageSegregatorService segregatorservice = applicationContext.getBean(MessageSegregatorService.class);
		segregatorservice.segregate(email);	
		log.info("************************Message Email Composing done***************************");
	}
	
	public void processEmail(Email email) throws MessagingException {
		log.info("-------------------------Email payload received for sending with id:: "+email.getMessageId()+ " for event :: "+email.getEventId()+"-------------------------");
		log.info(email.getBody());
		statusService.updateStatus(email.getEventId(), EventStatus.Status.COMPOSED, MessageType.EMAIL);
		//MAIL Sender SERVICE
		MailProcessingService service = applicationContext.getBean(MailProcessingService.class);
		service.process(email);		
	}

	public void composeSMS(IMessage message) {
		statusService.updateStatus(message.getEventId(), EventStatus.Status.PROCESSING, MessageType.SMS);
		//SMS COMPOSER SERVICE
	}
	
	public void processSMS(Sms sms) {
		//SMS COMPOSER SERVICE
		statusService.updateStatus(sms.getEventId(), EventStatus.Status.COMPOSED, MessageType.SMS);
	}

	public void composePN(IMessage message) {
		statusService.updateStatus(message.getEventId(), EventStatus.Status.PROCESSING, MessageType.PN);
		//PN COMPOSER SERVICE
	}
	

	public void processPN(Pn pn) {
		//PN COMPOSER SERVICE
		statusService.updateStatus(pn.getEventId(), EventStatus.Status.COMPOSED, MessageType.PN);
	}

	
	//<OBJECT> SENDER SERVICE
	
}
