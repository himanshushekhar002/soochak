package com.notificationlab.soochak.email.composer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import com.notificationlab.soochak.constants.TemplateType;
import com.notificationlab.soochak.dto.IMessage;
import com.notificationlab.soochak.email.Email;
import com.notificationlab.soochak.event.email.EmailEventDetail;
import com.notificationlab.soochak.event.email.EmailEventDetailRepository;
import com.notificationlab.soochak.template.processor.TemplateProcessor;

@Service
public class MailComposerService {

	private final TemplateProcessor templateProcessor;
	private final EmailEventDetailRepository repository;

	public MailComposerService(TemplateProcessor templateProcessor, EmailEventDetailRepository repository) {
		this.templateProcessor = templateProcessor;
		this.repository = repository;
	}

	public Email composeEmail(IMessage message) throws AddressException, MalformedURLException {
		long eventId = message.getEventId();
		Map<String, Object> data = message.getData();
		Optional<EmailEventDetail> optionalDetail = repository.findById(eventId);
		if (optionalDetail.isPresent()) {
			EmailEventDetail detail = optionalDetail.get();
			return Email.builder().eventId(eventId).messageId(message.getId())
					.toEmailIds(getToEmailIds(eventId, data, detail)).ccEmailIds(getCcEmailIds(eventId, data, detail))
					.bccEmailIds(getBccEmailIds(eventId, data, detail))
					.fromEmailId(getFromEmailId(eventId, data, detail))
					.replyToEmailId(getReplyToEmailId(eventId, data, detail))
					.fromDisplayName(getFromDisplayName(eventId, data, detail))
					.subject(getSubject(eventId, data, detail)).body(getBody(eventId, data))
					.attachments(getAttachments(eventId, data, detail))
					.cids(getCids(eventId, data, detail))
					.build();
		} else {
			// TODO : throw exception
			return null;
		}
	}

	private Set<InternetAddress> getToEmailIds(long eventId, Map<String, Object> data, EmailEventDetail detail)
			throws AddressException {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("tos");
			return attemptSetConversion(obj);
		} else {
			String plainText = templateProcessor.process(Locale.getDefault(), data, eventId + "_email_to.txt");
			String[] txtEmailids = plainText.split(",");
			return getList(txtEmailids);
		}
	}

	private Set<InternetAddress> getCcEmailIds(long eventId, Map<String, Object> data, EmailEventDetail detail)
			throws AddressException {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("ccs");
			return attemptSetConversion(obj);
		} else {
			String plainText = templateProcessor.process(Locale.getDefault(), data, eventId + "_email_cc.txt");
			String[] txtEmailids = plainText.split(",");
			return getList(txtEmailids);
		}
	}

	private Set<InternetAddress> getBccEmailIds(long eventId, Map<String, Object> data, EmailEventDetail detail)
			throws AddressException {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("bccs");
			return attemptSetConversion(obj);
		} else {
			String plainText = templateProcessor.process(Locale.getDefault(), data, eventId + "_email_bcc.txt");
			String[] txtEmailids = plainText.split(",");
			return getList(txtEmailids);
		}
	}

	private InternetAddress getFromEmailId(long eventId, Map<String, Object> data, EmailEventDetail detail)
			throws AddressException {
		return null;
//		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
//			Object obj = data.get("from");
//			if (obj != null) {
//				return new InternetAddress((String) obj);
//			}
//		} else {
//			String plainText = templateProcessor.process(Locale.getDefault(), data, eventId + "_email_from.txt");
//			return new InternetAddress(plainText);
//		}
//		return null;
	}

	private InternetAddress getReplyToEmailId(long eventId, Map<String, Object> data, EmailEventDetail detail)
			throws AddressException {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("replyto");
			if (obj != null) {
				return new InternetAddress((String) obj);
			}
		} else {
			String plainText = templateProcessor.process(Locale.getDefault(), data, eventId + "_email_replyto.txt");
			return new InternetAddress(plainText);
		}
		return null;
	}

	private String getFromDisplayName(long eventId, Map<String, Object> data, EmailEventDetail detail) {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("displayname");
			if (obj != null) {
				return (String) obj;
			}
		} else {
			return templateProcessor.process(Locale.getDefault(), data, eventId + "_email_displayname.txt");
		}
		return null;
	}

	private String getSubject(long eventId, Map<String, Object> data, EmailEventDetail detail) {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("subject");
			if (obj != null) {
				return (String) obj;
			}
		} else {
			return templateProcessor.process(Locale.getDefault(), data, eventId + "_email_subject.txt");
		}
		return null;
	}

	private Map<String, URL> getAttachments(long eventId, Map<String, Object> data, EmailEventDetail detail)
			throws MalformedURLException {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("attachments");
			return attemptMapConversion(obj);
		} else {
			String plainText = templateProcessor.process(Locale.getDefault(), data, eventId + "_email_attachment.txt");
			String[] txtUrls = plainText.split(",");
			return attemptMapConversion(txtUrls);
		}
	}
	
	private Map<String, URL> getCids(long eventId, Map<String, Object> data, EmailEventDetail detail)
			throws MalformedURLException {
		if (TemplateType.FIXED_FROM_PAYLOAD.equals(detail.getToTemplateType())) {
			Object obj = data.get("cids");
			return attemptMapConversion(obj);
		} else {
			String plainText = templateProcessor.process(Locale.getDefault(), data, eventId + "_email_cid.txt");
			String[] txtUrls = plainText.split(",");
			return attemptMapConversion(txtUrls);
		}
	}

	private String getBody(long eventId, Map<String, Object> data) {
		return templateProcessor.process(Locale.getDefault(), data, eventId + "_email_body.html");
	}

	private Set<InternetAddress> attemptSetConversion(Object obj) throws AddressException {
		if (obj instanceof List<?>) {
			List<String> lsEmailids = (List<String>) obj;
			return getList(lsEmailids);
		} else if (obj instanceof String) {
			String plainText = (String) obj;
			String[] txtEmailids = plainText.split(",");
			return getList(txtEmailids);
		} else if (obj instanceof String[]) {
			String[] txtEmailids = (String[]) obj;
			return getList(txtEmailids);
		} else {
			System.out.println("ALL CHECKS FAILED FOR CONVERSION OF OBJECT :: " + obj);
		}
		return Collections.emptySet();
	}

	private Map<String, URL> attemptMapConversion(Object obj) throws MalformedURLException {
		Map<String, URL> map = null;
		if (obj instanceof List<?>) {
			List<Map<String, String>> lsAttchmnts = (List<Map<String, String>>) obj;
			map = new HashMap<>();
			for (Map<String, String> mp : lsAttchmnts) {
				map.put(mp.get("name"), new URL(mp.get("url")));
			}
		} else if (obj instanceof Map<?, ?>) {
			Map<String, String> singleObject = (Map<String, String>) obj;
			map = new HashMap<>();
			map.put(singleObject.get("name"), new URL(singleObject.get("url")));
			return map;
		} else if (obj instanceof String[]) {
			String[] txtUrls = (String[]) obj;
			map = new HashMap<>();
			for (String txtUrl : txtUrls) {
				URL u = new URL(txtUrl);
				String path = u.getPath();
				String fileName = "";
				String[] pathSubstrings = path.split("/");
				if(pathSubstrings[pathSubstrings.length-1].length()>1) {
					fileName = pathSubstrings[pathSubstrings.length-1];
				}else {
					fileName = pathSubstrings[pathSubstrings.length-2];
				}				
				map.put(fileName, u);
			}
		} else {
			System.out.println("ALL CHECKS FAILED FOR CONVERSION OF OBJECT :: " + obj);
		}
		return map;
	}

	private Set<InternetAddress> getList(List<String> emailIds) throws AddressException {
		Set<InternetAddress> adressList = new HashSet<>();
		for (String txtEmailId : emailIds) {
			adressList.add(new InternetAddress(txtEmailId));
		}
		return adressList;
	}

	private Set<InternetAddress> getList(String[] emailIds) throws AddressException {
		Set<InternetAddress> adressList = new HashSet<>();
		for (String txtEmailId : emailIds) {
			adressList.add(new InternetAddress(txtEmailId));
		}
		return adressList;
	}

}
