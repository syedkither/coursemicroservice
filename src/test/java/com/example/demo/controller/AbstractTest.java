package com.example.demo.controller;

import java.io.IOException;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.component.Sender;
import com.example.demo.exception.CustomExceptionHandler;
import com.example.demo.repository.CourseMongoRepository;
import com.example.demo.service.CourseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class AbstractTest {

	protected MockMvc mvc;

	@InjectMocks
	protected CourseServiceController courseController;

	@MockBean
	protected CourseMongoRepository courseRepository;

	@Mock
	protected CourseService courseService;
	@Mock
	protected Sender sender;
	protected void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(courseController)
				.setControllerAdvice(new CustomExceptionHandler())
				.build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}