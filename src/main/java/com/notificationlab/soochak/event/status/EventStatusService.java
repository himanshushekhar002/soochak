package com.notificationlab.soochak.event.status;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificationlab.soochak.constants.MessageType;
import com.notificationlab.soochak.exception.ResourceNotFoundException;

@Service
public class EventStatusService {

	private final EventStatusRepository repository;
	
	@Autowired
	public EventStatusService(EventStatusRepository repository){
		this.repository = repository;
	}
	
	public EventStatusDto getStatus(long eventId) {
		Optional<EventStatusEntity> eventStatusOptional =  repository.findById(eventId);		
		if(eventStatusOptional.isPresent()) {
			return eventStatusOptional.get().getStatus();
		}else {
			throw new ResourceNotFoundException();
		}
	}
	
	public void createStatus(long eventId) {
		EventStatus emailStatus = EventStatus.builder().status(EventStatus.Status.PENDING).build(); 
		EventStatus smsStatus = EventStatus.builder().status(EventStatus.Status.PENDING).build();
		EventStatus pnStatus = EventStatus.builder().status(EventStatus.Status.PENDING).build();
		EventStatusDto eventStatusDto = EventStatusDto.builder().eventId(eventId).email(emailStatus).pn(pnStatus).sms(smsStatus).build();
		EventStatusEntity ent = EventStatusEntity.builder().eventId(eventId).status(eventStatusDto).build();
		saveStatus(ent);
	}

	public void updateStatus(long eventId, EventStatus.Status status, MessageType type) {
		updateStatus(eventId, status, type, null);
	}
	

	public void updateStatus(long eventId, EventStatus.Status status, MessageType type, String comment) {
		EventStatusEntity ent = null;
		Optional<EventStatusEntity> eventStatusOptional =  repository.findById(eventId);		
		if(eventStatusOptional.isPresent()) {
			ent = eventStatusOptional.get();
		}else {
			throw new ResourceNotFoundException();
		}
		EventStatus st = null;
		switch(type) {
		case EMAIL:
			st = ent.getStatus().getEmail();
			break;
		case SMS:
			st = ent.getStatus().getSms();
			break;
		case PN:
			st = ent.getStatus().getPn();
			break;
		default:
			throw new IllegalArgumentException("Invalid Message Type");
		}
		st.setStatus(status);
		st.setUpdateTime(new Date());
		st.setComment(comment);
		saveStatus(ent);
	}
	
	private void saveStatus(EventStatusEntity entity) {
		repository.save(entity);
	}	
}
