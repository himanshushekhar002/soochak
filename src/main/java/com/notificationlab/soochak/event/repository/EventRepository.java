package com.notificationlab.soochak.event.repository;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Long>{
	
	public String findUserIdById(long eventId);
}
