package com.notificationlab.soochak.email.smtp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notificationlab.soochak.email.smtp.repository.SmtpConfiguration;
import com.notificationlab.soochak.email.smtp.service.SmtpService;

@RestController
public class SmtpController {

	private final SmtpService smtpService;
	
	public SmtpController(final SmtpService smtpService) {
		this.smtpService = smtpService;
	}
	
	@PostMapping("/email/smtp/configuration")
	public SmtpConfiguration create(@RequestBody SmtpConfiguration smtpConfiguration) {
		return smtpService.create(smtpConfiguration);
	}
	
	@PutMapping("/email/smtp/configuration")
	public SmtpConfiguration update(@RequestBody SmtpConfiguration smtpConfiguration) {
		return smtpService.update(smtpConfiguration);
	}
}
