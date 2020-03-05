package com.example.demo.component;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	RabbitMessagingTemplate template;
	public static final String EXCHANGE_NAME = "spring-boot-exchange";
	public static final String QUEUE_ROUTINGKEY_ADD = "routingKeyadd-boot";
	public static final String QUEUE_ROUTINGKEY_REMOVE = "routingKeyremove-boot";
	
	@Autowired
	Sender(RabbitMessagingTemplate template) {
		this.template = template;
	}

	public void sendAR(Object message) {
		template.convertAndSend(EXCHANGE_NAME, QUEUE_ROUTINGKEY_ADD, message);
	}

	public void sendRR(Object message) {
		template.convertAndSend(EXCHANGE_NAME, QUEUE_ROUTINGKEY_REMOVE, message);
	}
}
