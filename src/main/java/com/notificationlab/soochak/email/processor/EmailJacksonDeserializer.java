package com.notificationlab.soochak.email.processor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.notificationlab.soochak.email.Email;

public class EmailJacksonDeserializer extends StdDeserializer<Email> {

	private static final long serialVersionUID = 1L;

	public EmailJacksonDeserializer() {
		this(null);
	}

	public EmailJacksonDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Email deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
		JsonNode node = jp.getCodec().readTree(jp);
		String messageId = (node.get("messageId").asText());
		long eventId = node.get("eventId").asLong();
		InternetAddress fromEmail = getInternetAddress(node.get("fromEmailId"));
		InternetAddress replyToEmailId = getInternetAddress(node.get("replyToEmailId"));
		String fromDisplayName = node.get("fromDisplayName").asText();
		String subject = node.get("subject").asText();
		String body = node.get("body").asText();
		Map<String, URL> attachments = getAttachments(node.get("attachments"));
		Map<String, URL> cids = getAttachments(node.get("cids"));
		Set<InternetAddress> toEmails = getEmails(node.get("toEmailIds"));
		Set<InternetAddress> ccEmails = getEmails(node.get("ccEmailIds"));
		Set<InternetAddress> bccEmailIds = getEmails(node.get("bccEmailIds"));
		return Email.builder().attachments(attachments).bccEmailIds(bccEmailIds).body(body).ccEmailIds(ccEmails)
				.eventId(eventId).fromDisplayName(fromDisplayName).fromEmailId(fromEmail).messageId(messageId)
				.replyToEmailId(replyToEmailId).subject(subject).toEmailIds(toEmails)
				.cids(cids)
				.build();
	}

	private InternetAddress getInternetAddress(JsonNode emailObject) throws UnsupportedEncodingException {
		if (emailObject.isNull()) {
			return null;
		}
		String address = emailObject.get("address").asText();
		String personal = null;
		if(!emailObject.get("personal").isNull()) {
			personal = emailObject.get("personal").asText();
		}		
		return new InternetAddress(address, personal);
	}

	private Map<String, URL> getAttachments(JsonNode attchmentNode) throws MalformedURLException {
		if (attchmentNode.isNull()) {
			return null;
		}
		Map<String, URL> map = new HashMap<>();
		Iterator<String> attchmentItr = attchmentNode.fieldNames();
		while (attchmentItr.hasNext()) {
			String key = attchmentItr.next();
			map.put(key, new URL(attchmentNode.get(key).asText()));
		}
		return map;
	}

	private Set<InternetAddress> getEmails(JsonNode emailListNode) throws UnsupportedEncodingException {
		if (emailListNode.isNull()) {
			return Collections.emptySet();
		}
		Set<InternetAddress> set = new HashSet<>();
		Iterator<JsonNode> emailNodes = emailListNode.elements();
		while (emailNodes.hasNext()) {
			set.add(getInternetAddress(emailNodes.next()));
		}
		return set;
	}
}