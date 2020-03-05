package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.document.Course;
import com.example.demo.document.CourseVO;

@Component
public class DefaultEntityMapper implements CourseEntityMapper {

    public void from(final Course course, final CourseVO coursevo) {
    	course.setCourseId(coursevo.getCourseId());
    	course.setActive(coursevo.getActive());
    	course.setDescription(coursevo.getDescription());
    	course.setFee(coursevo.getFee()); 
    	course.setTitle(coursevo.getTitle());
    	course.setId(coursevo.getId());
    }


    public Course from(final CourseVO coursevo) {
        final Course course = new Course();
        from(course, coursevo);
        return course;
    }
}
