package com.example.demo.component;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	RabbitMessagingTemplate template;

	@Autowired
	Sender(RabbitMessagingTemplate template) {
		this.template = template;
	}

	@Bean
	Queue queueaddcou() {
		return new Queue("StudentAQ", false);
	}

	@Bean
	Queue queueremovecou() {
		return new Queue("StudentRQ", false);
	}

	public void sendAR(Object message) {
		template.convertAndSend("StudentAQ", message);
	}

	public void sendRR(Object message) {
		template.convertAndSend("StudentRQ", message);
	}
}
