package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.document.Course;
import com.example.demo.document.CourseResponse;
import com.example.demo.mapper.CourseResponseMapper;
import com.example.demo.repository.CourseMongoRepository;
@Service
public class CourseService {
	
	@Autowired
	private CourseMongoRepository courseMongoRepository;
	
	
	@Autowired
    private CourseResponseMapper courseResponseMapper;
	
	public List<CourseResponse> getCoursesService(){
		 return courseResponseMapper.from(courseMongoRepository.findAllByActiveTrue());
	}
	
	public Course findByTitle(String title){
		return courseMongoRepository.findByTitle(title);
	}
	
	public void saveCourse(Course course){
		courseMongoRepository.save(course);
	}
	public Course findByCourseId(String courseID){
		return courseMongoRepository.findByCourseId(Integer.parseInt((courseID)));
	}
}
