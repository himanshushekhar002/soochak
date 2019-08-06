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
import com.notificationlab.soochak.email.processor.MailSenderService;
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
	
	public void composeSMS(IMessage message) {
		statusService.updateStatus(message.getEventId(), EventStatus.Status.PROCESSING, MessageType.SMS);
		//SMS COMPOSER SERVICE
	}
	
	public void processSMS(Sms sms) {
		//SMS SENDER SERVICE
		statusService.updateStatus(sms.getEventId(), EventStatus.Status.COMPOSED, MessageType.SMS);
	}

	public void composePN(IMessage message) {
		statusService.updateStatus(message.getEventId(), EventStatus.Status.PROCESSING, MessageType.PN);
		//PN COMPOSER SERVICE
	}
	
	public void processPN(Pn pn) {
		//PN SENDER SERVICE
		statusService.updateStatus(pn.getEventId(), EventStatus.Status.COMPOSED, MessageType.PN);
	}	
	//<OBJECT> SENDER SERVICE	
}
