package com.notificationlab.soochak.email.sender;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.lang.Nullable;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
final class SoochakMailSender extends JavaMailSenderImpl {

	private static final String HEADER_MESSAGE_ID = "Message-ID";

	
	public SoochakMailSender(String host, int port, String username, String password){
		super.setHost(host);
		super.setPort(port);
		super.setUsername(username);
		super.setPassword(password);		
	}
	
	/**
	 * Actually send the given array of MimeMessages via JavaMail.
	 * 
	 * @param mimeMessages     the MimeMessage objects to send
	 * @param originalMessages corresponding original message objects that the
	 *                         MimeMessages have been created from (with same array
	 *                         length and indices as the "mimeMessages" array), if
	 *                         any
	 * @throws org.springframework.mail.MailAuthenticationException in case of
	 *         authentication failure
	 * @throws org.springframework.mail.MailSendException in case of failure when
	 *         sending a message
	 */
	@Override
	protected void doSend(MimeMessage[] mimeMessages, @Nullable Object[] originalMessages) {
		Map<Object, Exception> failedMessages = new LinkedHashMap<>();
		Transport transport = null;

		for (int i = 0; i < mimeMessages.length; i++) {

			// Check transport connection first...
			if (transport == null || !transport.isConnected()) {
				try {
					transport = getTransport();
				} catch (AuthenticationFailedException ex) {
					throw new MailAuthenticationException(ex);
				} catch (Exception ex) {
					// Effectively, all remaining messages failed...
					for (int j = i; j < mimeMessages.length; j++) {
						Object original = (originalMessages != null ? originalMessages[j] : mimeMessages[j]);
						failedMessages.put(original, ex);
					}
					throw new MailSendException("Mail server connection failed", ex, failedMessages);
				}
			}

			// Send message via current transport...
			MimeMessage mimeMessage = mimeMessages[i];
			try {
				send(mimeMessage, transport);
			} catch (Exception ex) {
				Object original = (originalMessages != null ? originalMessages[i] : mimeMessage);
				failedMessages.put(original, ex);
			}
		}
		if (!failedMessages.isEmpty()) {
			throw new MailSendException(failedMessages);
		}
	}

	private Transport getTransport() throws MessagingException {
		Transport transport = TransportCacheService.getTransport(getUsername());
		if (transport == null || !transport.isConnected()) {
			if (transport != null) {
				try {
					transport.close();
				} catch (Exception ex) {
					// Ignore - we're reconnecting anyway
				}
				transport = null;
			}
			transport = connectTransport();
			TransportCacheService.putTransport(getUsername(), transport);
			log.info("New Transport created for cache");
		}else {
			log.info("Returning Transport from cache");
		}
		return transport;
	}

	private void send(MimeMessage mimeMessage, Transport transport) throws Exception {
		if (mimeMessage.getSentDate() == null) {
			mimeMessage.setSentDate(new Date());
		}
		String messageId = mimeMessage.getMessageID();
		if (messageId != null) {
			// Preserve explicitly specified message id...
			mimeMessage.setHeader(HEADER_MESSAGE_ID, messageId);
		}
		mimeMessage.saveChanges();
		Address[] addresses = mimeMessage.getAllRecipients();
		transport.sendMessage(mimeMessage, (addresses != null ? addresses : new Address[0]));
	}
}
