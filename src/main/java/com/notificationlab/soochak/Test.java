package com.notificationlab.soochak;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationlab.soochak.dto.Message;

@Configuration
public class Test implements ApplicationRunner{

	public void run(ApplicationArguments args) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> name = new HashMap<String, Object>();
		name.put("fname", "himanshu");
		name.put("lname", "shekahr");
		map.put("name", name);
		Message msg = new Message(1000000000l, map, null);
		String content = mapper.writeValueAsString(msg);
		System.out.println("=====================================");
		System.out.println(content);
		System.out.println("=====================================");
//		TypeReference<Message<Map<String, Object>>> typeRef 
//        = new TypeReference<Message<Map<String, Object>>>() {};
//		TypeReference<Map<String, Object>> typeRef 
//        = new TypeReference<Map<String, Object>>() {};
        Message msg1 = mapper.readValue(content, Message.class);
		System.out.println(msg1);
	}
	
}
