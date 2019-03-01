package com.notificationlab.soochak.template.processor;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.notificationlab.soochak.dto.IMessage;

@Service
public class TemplateProcessor {

	private final TemplateEngine templateEngine;

	@Autowired
	public TemplateProcessor(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String process(Locale locale, IMessage message, String template) {
		return process(locale, message.getData(), template);
	}
	
	public String process(Locale locale, Map<String, Object> data, String template) {
		final Context ctx = new Context(locale);
		ctx.setVariables(data);
		return this.templateEngine.process(template, ctx);
	}
}
