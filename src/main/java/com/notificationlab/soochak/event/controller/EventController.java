package com.notificationlab.soochak.event.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.notificationlab.soochak.event.repository.Event;
import com.notificationlab.soochak.event.service.EventService;

@RestController
public class EventController {

	private final EventService eventService;
	
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}
	
	@PostMapping("/event")
	public Event create(@RequestBody Event event) {
		return eventService.create(event);
	}
	
	@PutMapping("/event")
	public Event update(@RequestBody Event event) {
		return eventService.update(event);
	}
}
