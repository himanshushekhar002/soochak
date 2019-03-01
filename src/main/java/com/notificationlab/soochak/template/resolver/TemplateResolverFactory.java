package com.notificationlab.soochak.template.resolver;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.springframework.context.ApplicationContext;
import org.thymeleaf.templatemode.TemplateMode;

public final class TemplateResolverFactory {
	
	private TemplateResolverFactory(){}

	public static EmailTemplateResolver getEmailTemplateResolver(ApplicationContext applicationContext,
			String emailTemplateFolderPath) {

		final EmailTemplateResolver templateResolver = new EmailTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(2));
		templateResolver.setResolvablePatterns(Collections.singleton("*_email_*"));
		templateResolver.setPrefix("file:" + emailTemplateFolderPath);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		templateResolver.setCacheable(false);
		templateResolver.setApplicationContext(applicationContext);
		return templateResolver;
	}

	public static SMSTemplateResolver getSMSTemplateResolver(ApplicationContext applicationContext,
			String smsTemplateFolderPath) {
		final SMSTemplateResolver templateResolver = new SMSTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(1));
		templateResolver.setResolvablePatterns(Collections.singleton("*_sms_*"));
		templateResolver.setPrefix("file:" + smsTemplateFolderPath);
		templateResolver.setSuffix(".txt");
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		templateResolver.setCacheable(false);
		templateResolver.setApplicationContext(applicationContext);
		return templateResolver;
	}

	public static PNTemplateResolver getPNTemplateResolver(ApplicationContext applicationContext,
			String pnTemplateFolderPath) {
		final PNTemplateResolver templateResolver = new PNTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(1));
		templateResolver.setResolvablePatterns(Collections.singleton("*_pn_*"));
		templateResolver.setPrefix("file:" + pnTemplateFolderPath);
		templateResolver.setSuffix(".txt");
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		templateResolver.setCacheable(false);
		templateResolver.setApplicationContext(applicationContext);
		return templateResolver;
	}
}
