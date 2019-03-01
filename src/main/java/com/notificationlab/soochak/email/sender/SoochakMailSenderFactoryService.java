package com.notificationlab.soochak.email.sender;

import java.util.Optional;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.notificationlab.soochak.email.smtp.repository.SmtpConfigRepository;
import com.notificationlab.soochak.email.smtp.repository.SmtpConfiguration;
import com.notificationlab.soochak.event.email.EmailEventDetail;
import com.notificationlab.soochak.event.email.EmailEventDetailRepository;

@Service
public final class SoochakMailSenderFactoryService {

	private static final long DEFAULT_SMTP_ID = 10000000000l;
	
	private final SmtpConfigRepository smtpConfigRepository;
	private final EmailEventDetailRepository eventRepository;
	
	public SoochakMailSenderFactoryService(SmtpConfigRepository smtpConfigRepository, EmailEventDetailRepository eventRepository){
		this.eventRepository = eventRepository;
		this.smtpConfigRepository = smtpConfigRepository;
	}
	
	public JavaMailSender getJavaMailSenderByEventId(long eventId) {
		Optional<EmailEventDetail> optionalDetail = eventRepository.findById(eventId);
		if(optionalDetail.isPresent()) {
			return getJavaMailSenderBySmtpId(optionalDetail.get().getSmtpConfigId());
		}else{
			//TODO : throw exception
			return null;
		}
	}
	
	public JavaMailSender getJavaMailSenderBySmtpId(long smtpId) {
		Optional<SmtpConfiguration> smtpConfigOptional = smtpConfigRepository.findById(smtpId);
		SmtpConfiguration smtpConfig = null;
		Properties javaMailProperties = new Properties();
		if(smtpConfigOptional.isPresent()) {
			smtpConfig = smtpConfigOptional.get();			
			javaMailProperties.put("mail.smtp.user", smtpConfig.getUsername());
			javaMailProperties.put("mail.smtp.host", smtpConfig.getHost());
			javaMailProperties.put("mail.smtp.port", smtpConfig.getPort());
			javaMailProperties.put("mail.smtp.connectiontimeout", 60000);
			javaMailProperties.put("mail.smtp.timeout", 60000);
			javaMailProperties.put("mail.smtp.starttls.enable", "true");
		}else {
			//TODO : throw Exception
		}
		SoochakMailSender mailSender = new SoochakMailSender(smtpConfig.getHost(),smtpConfig.getPort(),smtpConfig.getUsername(),smtpConfig.getPassword());
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
	
	public JavaMailSender getDefaultJavaMailSender() {		
		return getJavaMailSenderBySmtpId(DEFAULT_SMTP_ID);
	}
	
}
