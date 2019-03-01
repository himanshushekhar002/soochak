package com.notificationlab.soochak.email;

import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.notificationlab.soochak.email.processor.EmailJacksonDeserializer;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(using=EmailJacksonDeserializer.class)
public class Email {

	private String messageId;
	private long eventId;
	private Set<InternetAddress> toEmailIds;
	private Set<InternetAddress> ccEmailIds;
	private Set<InternetAddress> bccEmailIds;
	private InternetAddress fromEmailId;
	private InternetAddress replyToEmailId;
	private String fromDisplayName;
	private Map<String,URL> attachments;
	private Map<String,URL> cids;
	private String subject;
	private String body;
	
}
