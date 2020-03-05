package com.example.demo.mapper;

import com.example.demo.document.Course;
import com.example.demo.document.CourseVO;

public interface CourseEntityMapper {
	 void from(final Course course, final CourseVO coursevo);
	 Course from(final CourseVO coursevo);
}
