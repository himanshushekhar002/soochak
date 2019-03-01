package com.notificationlab.soochak.event.email.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.notificationlab.soochak.constants.FileType;
import com.notificationlab.soochak.constants.MessageType;
import com.notificationlab.soochak.event.email.EmailEventDetail;
import com.notificationlab.soochak.event.email.EmailEventDetailRepository;
import com.notificationlab.soochak.event.email.dto.EmailEventDetailDto;
import com.notificationlab.soochak.template.repository.TemplateRepository;

@Service
public class EmailEventDetailService {

	private final EmailEventDetailRepository emailEventDetailrepository;
	private final TemplateRepository templateRepository;
	
	public EmailEventDetailService(final EmailEventDetailRepository repository, final TemplateRepository templateRepository) {
		this.emailEventDetailrepository = repository;
		this.templateRepository = templateRepository;
	}
	
	public EmailEventDetail create(EmailEventDetailDto emailEventDetail) throws IOException {
		EmailEventDetail e = emailEventDetailrepository.save(emailEventDetail.getMetaInfo());
		long eventId = emailEventDetail.getMetaInfo().getEventId();
		String tos = emailEventDetail.getTos();
		String ccs = emailEventDetail.getCcs();
		String bccs = emailEventDetail.getBccs();
		String attachments = emailEventDetail.getAttachments();
		String cids = emailEventDetail.getCids();
		String body = emailEventDetail.getBody();
		String displayName = emailEventDetail.getDisplayName();
		String replyTo = emailEventDetail.getReplyTo();
		String subject = emailEventDetail.getSubject();
		templateRepository.save(MessageType.EMAIL, eventId+"_email_to", tos, FileType.TXT);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_cc", ccs, FileType.TXT);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_bcc", bccs, FileType.TXT);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_attachment", attachments, FileType.TXT);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_cid", cids, FileType.TXT);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_body", body, FileType.HTML);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_displayname", displayName, FileType.TXT);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_replyTo", replyTo, FileType.TXT);
		templateRepository.save(MessageType.EMAIL, eventId+"_email_subject", subject, FileType.TXT);
		return e;
	}
	
	public EmailEventDetail update(EmailEventDetailDto emailEventDetail) throws IOException {
		//TODO CHECK FOR EXISTENCE VALIDATION
		return create(emailEventDetail);
	}
}
