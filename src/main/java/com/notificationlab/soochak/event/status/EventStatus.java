package com.notificationlab.soochak.event.status;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventStatus {

	private Date updateTime;
	private Status status;
	private String comment;
	
	
	
	public enum Status{
		NOTAPPLICABLE, PENDING, PROCESSING, COMPOSED, SENT, ERROR 
	}
}
