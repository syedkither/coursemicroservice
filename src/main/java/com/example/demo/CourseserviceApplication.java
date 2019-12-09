package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.document.Course;
import com.example.demo.repository.CourseMongoRepository;

@SpringBootApplication
public class CourseserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseserviceApplication.class, args);
	}

	@Bean
	public CommandLineRunner mappingDemo(CourseMongoRepository courseMongoRepository) {
		return args -> {
			addSampleData(courseMongoRepository);
			listAll(courseMongoRepository);
		};
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
