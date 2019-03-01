package com.notificationlab.soochak.event.service;

import org.springframework.stereotype.Service;

import com.notificationlab.soochak.event.repository.Event;
import com.notificationlab.soochak.event.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventService {

	private final EventRepository repository;
	
	EventService(final EventRepository repository){
		this.repository =  repository;
	}
	
	public Event create(Event event) {
		log.info("Event received for creation"+event);
		return repository.save(event);
	}
	
	public Event update(Event event) {
		log.info("Event received for update"+event);
		if(!(repository.findById(event.getId()).isPresent())) {
			//Throw NOT FOUND EXCEPTION
			return null;
		}
		return repository.save(event);
	}
}
