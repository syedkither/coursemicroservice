package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.document.Course;

@Repository
public interface CourseMongoRepository extends MongoRepository<Course, Integer> {

	Course findByTitle(String title);

	Course findByCourseId(Integer courseId);
	
	List<Course> findAllByActiveTrue();

}
