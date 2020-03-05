package com.example.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.document.Course;
import com.example.demo.document.CourseResponse;
import com.example.demo.document.CourseResponse.CourseResponseBuilder;
import com.example.demo.document.CourseVO;

public class CourseServiceControllerTest extends AbstractTest {
	private static final String CREATE_URL = "/course/add";
	private static final String GET_URL = "/course/get";
	private static final String REMOVE_URL = "/course/remove?courseID=1";
	 @Override
	   @Before
	   public void setUp() {
	      super.setUp();
	   }
	
	 
	 public Course buildCourse(){
		   return new Course("Rest", "Spring Restful", true, 5, 5.0);
	 }
	 
	 @Test
	 public void get() throws Exception {
		 
	      
	      List<CourseResponse> aoCourse = new ArrayList();
	      aoCourse.add(courseResponse());
	      when(courseService.getCoursesService()).thenReturn(aoCourse);
	      
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GET_URL)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      CourseVO[] coursevo = mapFromJson(content, CourseVO[].class);
	      assertTrue(coursevo[0].getTitle().equalsIgnoreCase("Rest"));
	 }
	 @Test
	 public void create() throws Exception {
		  Course course = buildCourse();
	      when(courseService.findByTitle(any(String.class))).thenReturn(course);
	      
	      String inputJson = super.mapToJson(courseResponse());
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(CREATE_URL)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	 }
	 private CourseResponse courseResponse(){
		   	Course course = buildCourse();
		    Set<Course> courseSet = new HashSet<>(); 
		    courseSet.add(course);
		    final CourseResponseBuilder courseResponseBuilder = CourseResponse.builder();
		    courseResponseBuilder.withActive(true)
		        .withCourseId(5)
		        .withDescription("Spring Restful")
		        .withFee(5.0)
		        .withId("1")
		        .withTitle("Rest"); 
		    	return courseResponseBuilder.build();
	   }
	 @Test
	 public void delete() throws Exception {
		  Course course = buildCourse();
		  when(courseService.findByCourseId(any(String.class))).thenReturn(course);
		  course.setActive(false);
		  doNothing().when(courseService).saveCourse(any(Course.class));
		  doNothing().when(sender).sendRR(any(Course.class));
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(REMOVE_URL)).andReturn();
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	 }
}
