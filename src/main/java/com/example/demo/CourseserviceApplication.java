package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.document.Course;
import com.example.demo.repository.CourseMongoRepository;

@EnableDiscoveryClient
@SpringBootApplication
@EnableWebMvc
public class CourseserviceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CourseserviceApplication.class, args);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		/*
		 * Spring will look for path extension first, if that is not present
		 * then will look for path parameter. And if both of these are not
		 * available in the input request, then default content type will be
		 * returned back
		 */
		configurer.favorPathExtension(true).ignoreUnknownPathExtensions(true).favorParameter(true)
				.parameterName("mediaType").ignoreAcceptHeader(false).defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Bean
	public CommandLineRunner mappingDemo(CourseMongoRepository courseMongoRepository) {
		return args -> {
			cleanData(courseMongoRepository);
			addSampleData(courseMongoRepository);
			listAll(courseMongoRepository);
		};
	}

	public void cleanData(CourseMongoRepository courseMongoRepository) {
		System.out.println("Removing all data to Atlas Mongo DB");
		courseMongoRepository.deleteAll();
	}

	public void addSampleData(CourseMongoRepository courseMongoRepository) {
		System.out.println("Adding sample data to Atlas Mongo DB");
		courseMongoRepository.save(new Course("ML", "Machine Learning", true, 1, 1500.0));
		courseMongoRepository.save(new Course("DS", "Database Systems", true, 2, 800.0));
		courseMongoRepository.save(new Course("WB", "Web Basics", true, 3, 130.0));
	}

	public void listAll(CourseMongoRepository courseMongoRepository) {
		System.out.println("Listing sample data from Atlas Mongo DB");
		courseMongoRepository.findAll().forEach(c -> System.out.println(c));
	}

}
