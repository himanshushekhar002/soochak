package com.notificationlab.soochak.event.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String userId;
	@Column(columnDefinition = "BOOLEAN")
	private boolean emailEnabled;
	@Column(columnDefinition = "BOOLEAN")
	private boolean smsEnabled;
	@Column(columnDefinition = "BOOLEAN")
	private boolean pnEnabled;
}
