package com.notificationlab.soochak.event.email;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.notificationlab.soochak.constants.TemplateType;

import lombok.Data;

@Entity
@Data
public class EmailEventDetail {

	@Id
	private long eventId;
	private long smtpConfigId;
	private TemplateType toTemplateType;
	private TemplateType ccTemplateType;
	private TemplateType bccTemplateType;
	private TemplateType replyToTemplateType;
	private TemplateType displayNameTemplateType;
	private TemplateType subjectTemplateType;
	private TemplateType attachmentTemplateType;
	private TemplateType cidsTemplateType;
}
