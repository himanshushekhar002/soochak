package com.notificationlab.soochak.email.processor;

import java.io.IOException;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.InputStreamSource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationlab.soochak.constants.KafkaConstant;
import com.notificationlab.soochak.constants.MessageType;
import com.notificationlab.soochak.email.Email;
import com.notificationlab.soochak.email.sender.SoochakMailSenderFactoryService;
import com.notificationlab.soochak.event.status.EventStatus;
import com.notificationlab.soochak.event.status.EventStatusService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public final class MailSenderService {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static ExecutorService executorService = Executors.newFixedThreadPool(10);
	private static final AtomicInteger COUNTER = new AtomicInteger(0);
	
	private final SoochakMailSenderFactoryService soochakMailSenderFactoryService;
	private final EventStatusService statusService;
	
	public MailSenderService(final SoochakMailSenderFactoryService soochakMailSenderFactoryService,final EventStatusService statusService){
		this.soochakMailSenderFactoryService=soochakMailSenderFactoryService;
		this.statusService = statusService;
	}
	
	@KafkaListener(topics = KafkaConstant.TOPIC_NAME_EMAIL_COMPOSED, groupId = KafkaConstant.GROUPID)
	public void listenEmailComposedQueue(String message) throws IOException, MessagingException {
			log.debug("Received Messasge in group " + KafkaConstant.TOPIC_NAME_EMAIL_COMPOSED + " : " + message);
			Email email = mapper.readValue(message, Email.class);
			processEmail(email);
	}
	
	private void processEmail(Email email) throws MessagingException {
		log.info("-------------------------Email payload received for sending with id:: "+email.getMessageId()+ " for event :: "+email.getEventId()+"-------------------------");
		log.info(email.getBody());
		statusService.updateStatus(email.getEventId(), EventStatus.Status.COMPOSED, MessageType.EMAIL);
		//MAIL Sender SERVICE
		process(email);		
	}
	
	private void process(final Email email){
		log.info("Executor Pool Status :: "+COUNTER.intValue());
		executorService.execute(()->{
			try {
				COUNTER.incrementAndGet();
				process(email, 
						soochakMailSenderFactoryService.getJavaMailSenderByEventId(email.getEventId()));
				log.info("************************Message EMail Processing done***************************");
			} catch (MessagingException e) {
				log.error("EXCEPTION IN PROCESSING EMAIL",e);
			} finally {
				COUNTER.decrementAndGet();
			}
		});		
	}

	
	private void process(final MimeMessage emailMessage, final JavaMailSender javaMailSender) {		
		javaMailSender.send(emailMessage);
	}
	
	private void process(final Email email, final JavaMailSender javaMailSender) throws MessagingException {
		MimeMessage emailMessage = javaMailSender.createMimeMessage();		
		MimeMessageHelper helper = new MimeMessageHelper(emailMessage, true);
		Set<Entry<String, URL>> attachmentEntrySet = email.getAttachments().entrySet();
		Set<Entry<String, URL>> cidEntrySet = email.getCids().entrySet();
		for(Entry<String, URL> entry : attachmentEntrySet) {
			InputStreamSource is = ()->entry.getValue().openConnection().getInputStream();			
			helper.addAttachment(entry.getKey(), is);
		}
		for(Entry<String, URL> entry : cidEntrySet) {
			InputStreamSource is = ()->entry.getValue().openConnection().getInputStream();
			int indexOfImgExtension = entry.getKey().lastIndexOf('.');
			if(indexOfImgExtension>-1 && indexOfImgExtension<(entry.getKey().length()-1)) {
				String ext = entry.getKey().substring(indexOfImgExtension+1);
				helper.addInline(entry.getKey(), is, "image/"+ext);
			}
		}

		for(InternetAddress toAddr : email.getToEmailIds()) {
			helper.addTo(toAddr);
		}
		for(InternetAddress ccAddr : email.getCcEmailIds()) {
			helper.addCc(ccAddr);
		}
		for(InternetAddress bccAddr : email.getBccEmailIds()) {
			helper.addBcc(bccAddr);
		}
		helper.setReplyTo(email.getReplyToEmailId());
		helper.setSubject(email.getSubject());
		helper.setText(email.getBody(),true);
		process(emailMessage, javaMailSender);
		statusService.updateStatus(email.getEventId(), EventStatus.Status.SENT, MessageType.EMAIL);
	}
}
