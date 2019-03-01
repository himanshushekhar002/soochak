package com.notificationlab.soochak.event.status;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventStatusDto {

	private long eventId;
	private EventStatus email;
	private EventStatus sms;
	private EventStatus pn;
}
