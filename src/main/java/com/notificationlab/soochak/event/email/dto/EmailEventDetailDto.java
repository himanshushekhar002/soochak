package com.notificationlab.soochak.event.email.dto;

import com.notificationlab.soochak.event.email.EmailEventDetail;

import lombok.Data;

@Data
public class EmailEventDetailDto {

	private EmailEventDetail metaInfo;
	private String tos;
	private String ccs;
	private String bccs;
	private String subject;
	private String replyTo;
	private String displayName;
	private String attachments;
	private String cids;
	private String body;
	
}
