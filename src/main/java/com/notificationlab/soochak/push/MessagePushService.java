package com.notificationlab.soochak.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationlab.soochak.dto.IMessage;
import com.notificationlab.soochak.email.Email;

@Service
public class MessagePushService {
	
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper mapper = new ObjectMapper();
    
    @Autowired
	public MessagePushService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
    public void push(String topic, Email message) throws JsonProcessingException {
    	push(topic, message, message.getMessageId());
	}
    
    public IMessage push(String topic, IMessage message) throws JsonProcessingException {
		push(topic, message, message.getId());
		return message;
	}
	
	private void push(String topic, Object message, String id) throws JsonProcessingException {
		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, id, mapper.writeValueAsString(message));
		future.addCallback(new KafkaCallback());
	}
	
	private static final class KafkaCallback implements  ListenableFutureCallback<SendResult<String, String>>{

		@Override
		public void onSuccess(SendResult<String, String> result) {
			String message = result.getProducerRecord().value();
			System.out.println("Sent message=[" + message + 
		              "] with offset=[" + result.getRecordMetadata().offset() + "]");
			
		}

		@Override
		public void onFailure(Throwable ex) {
			System.out.println("Unable to send message due to : " + ex.getMessage());			
		}		
	}
}
