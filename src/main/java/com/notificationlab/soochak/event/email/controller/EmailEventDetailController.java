package com.notificationlab.soochak.event.email.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notificationlab.soochak.event.email.EmailEventDetail;
import com.notificationlab.soochak.event.email.dto.EmailEventDetailDto;
import com.notificationlab.soochak.event.email.service.EmailEventDetailService;

@RestController
public class EmailEventDetailController {

	private final EmailEventDetailService emailEventDetailService;
	public EmailEventDetailController(final EmailEventDetailService emailEventDetailService) {
		this.emailEventDetailService = emailEventDetailService;
	}
	
	@PostMapping("/event/email/")
	public EmailEventDetail create(@RequestBody EmailEventDetailDto emailEventDetail) throws IOException {
		return emailEventDetailService.create(emailEventDetail);
	}
	
	@PutMapping("/event/email/")
	public EmailEventDetail update(@RequestBody EmailEventDetailDto emailEventDetail) throws IOException {
		return emailEventDetailService.update(emailEventDetail);
	}
}
