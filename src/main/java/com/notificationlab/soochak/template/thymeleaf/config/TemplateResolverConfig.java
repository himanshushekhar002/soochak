package com.notificationlab.soochak.template.thymeleaf.config;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.notificationlab.soochak.template.repository.TemplateRepository;
import com.notificationlab.soochak.template.resolver.TemplateResolverFactory;

@Configuration
public class TemplateResolverConfig {

	private final TemplateEngine templateEngine;
	
	private final TemplateRepository templateRepository;

	private final ApplicationContext applicationContext;

	public TemplateResolverConfig(TemplateEngine templateEngine, TemplateRepository templateRepository, ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
		this.templateEngine = templateEngine;
		this.templateRepository = templateRepository;
	}
	
	@PostConstruct
	public void configureTemplateEngine() {
		// Resolver for HTML emails (except the editable one)
		templateEngine.addTemplateResolver(emailTemplateResolver());
		// Resolver for TEXT
		templateEngine.addTemplateResolver(smsTemplateResolver());
		// Resolver for PN
		templateEngine.addTemplateResolver(pnTemplateResolver());
		// Resolver for HTML editable emails (which will be treated as a String)
	}

	private ITemplateResolver emailTemplateResolver() {
		return TemplateResolverFactory.getEmailTemplateResolver(applicationContext, templateRepository.getEmailTemplatePath());
	}


	private ITemplateResolver smsTemplateResolver() {
		return TemplateResolverFactory.getSMSTemplateResolver(applicationContext, templateRepository.getSmsTemplatePath());
	}

	private ITemplateResolver pnTemplateResolver() {
		return TemplateResolverFactory.getPNTemplateResolver(applicationContext, templateRepository.getPnTemplatePath());
	}

//	@Override
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		this.applicationContext = applicationContext;
//
//	}
//
}
