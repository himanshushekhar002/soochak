package com.notificationlab.soochak.template.resolver;

import java.util.Map;

import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

public abstract class AbstractTemplateResolver extends SpringResourceTemplateResolver {

	@Override
	protected ITemplateResource computeTemplateResource(final IEngineConfiguration configuration,
			final String ownerTemplate, final String template, final String resourceName,
			final String characterEncoding, final Map<String, Object> templateResolutionAttributes) {
		beforeComputingTemplate(configuration, ownerTemplate, template,
				resourceName, characterEncoding, templateResolutionAttributes);
		
		ITemplateResource templateResource = super.computeTemplateResource(configuration, ownerTemplate, template,
				resourceName, characterEncoding, templateResolutionAttributes);
		
		afterComputingTemplate(configuration, ownerTemplate, template,
				resourceName, characterEncoding, templateResolutionAttributes);
		return templateResource;
	}

	protected abstract void beforeComputingTemplate(final IEngineConfiguration configuration,
			final String ownerTemplate, final String template, final String resourceName,
			final String characterEncoding, final Map<String, Object> templateResolutionAttributes);

	protected abstract void afterComputingTemplate(final IEngineConfiguration configuration,
			final String ownerTemplate, final String template, final String resourceName,
			final String characterEncoding, final Map<String, Object> templateResolutionAttributes);

}
