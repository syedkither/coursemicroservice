package com.example.demo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.document.Course;
import com.example.demo.document.CourseResponse;
import com.example.demo.document.CourseResponse.CourseResponseBuilder;

@Component
public class DefaultResponseMapper implements CourseResponseMapper {

    public List<CourseResponse> from(final List<Course> course) {
        return course.stream().map(this::from).collect(Collectors.toCollection(ArrayList::new));
    }
    
    public CourseResponse from(Course course){
    	final CourseResponseBuilder courseResponseBuilder = CourseResponse.builder();
    	courseResponseBuilder.withActive(course.getActive())
    	.withCourseId(course.getCourseId())
    	.withDescription(course.getDescription())
    	.withFee(course.getFee())
    	.withId(course.getId())
    	.withTitle(course.getTitle());               
    	return courseResponseBuilder.build();
    }
}
