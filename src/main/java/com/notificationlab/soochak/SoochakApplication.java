package com.notificationlab.soochak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/*
 * Greatly Inspired By 
 * https://www.thymeleaf.org/doc/articles/springmail.html
 */
@SpringBootApplication
public class SoochakApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SoochakApplication.class, args);
	}
}
