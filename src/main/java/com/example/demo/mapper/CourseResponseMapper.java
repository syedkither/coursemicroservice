package com.example.demo.mapper;

import java.util.List;

import com.example.demo.document.Course;
import com.example.demo.document.CourseResponse;

public interface CourseResponseMapper {
	List<CourseResponse> from(final List<Course> course);
}
