package com.notificationlab.soochak.template.resolver;

import java.util.Map;

import org.springframework.util.StopWatch;
import org.thymeleaf.IEngineConfiguration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class EmailTemplateResolver extends AbstractTemplateResolver{

	
	@Override
	public void beforeComputingTemplate(final IEngineConfiguration configuration,
			final String ownerTemplate, final String template, final String resourceName,
			final String characterEncoding, final Map<String, Object> templateResolutionAttributes) {
		log.debug("RESOURCE NAME :: "+resourceName);
		
	}

	@Override
	protected void afterComputingTemplate(final IEngineConfiguration configuration,
			final String ownerTemplate, final String template, final String resourceName,
			final String characterEncoding, final Map<String, Object> templateResolutionAttributes) {
		log.debug("Computation Done for resource :: "+resourceName);		
	}
	
}
