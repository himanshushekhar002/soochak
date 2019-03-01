package com.notificationlab.soochak.event.status;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
class EventStatusEntity {

	private long eventId;
	
	@Type(type = "json")
    @Column(columnDefinition = "json")
	private EventStatusDto status;
}
