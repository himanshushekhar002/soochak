package com.notificationlab.soochak.email.smtp.service;

import org.springframework.stereotype.Service;

import com.notificationlab.soochak.email.smtp.repository.SmtpConfigRepository;
import com.notificationlab.soochak.email.smtp.repository.SmtpConfiguration;

@Service
public class SmtpService {

	private final SmtpConfigRepository repository;
	
	SmtpService(SmtpConfigRepository repository){
		this.repository = repository;
	}
	
	public SmtpConfiguration create(SmtpConfiguration smtpConfiguration) {
		return repository.save(smtpConfiguration);
	}
	
	public SmtpConfiguration update(SmtpConfiguration smtpConfiguration) {
		return repository.save(smtpConfiguration);
	}
}
