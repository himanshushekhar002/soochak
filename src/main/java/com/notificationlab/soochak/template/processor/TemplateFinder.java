package com.notificationlab.soochak.template.processor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class TemplateFinder {

	public Set<String> getAllTemplates(String eventId){
		return new HashSet<String>(Arrays.asList(eventId+"-email",eventId+"-sms",eventId+"-pn"));
	}
	
}
