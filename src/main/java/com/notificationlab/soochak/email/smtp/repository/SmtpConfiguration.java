package com.notificationlab.soochak.email.smtp.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SmtpConfiguration {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String userId;
	private String host;
	private int port;
	private String protocol;
	private String username;
	private String password;
	
}