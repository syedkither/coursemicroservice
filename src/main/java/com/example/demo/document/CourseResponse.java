package com.example.demo.document;

import org.springframework.util.StringUtils;

import com.example.demo.component.ErrorMessageConstants;

public class CourseResponse {
	private String id;
	private Integer courseId;
	private String title;
	private String description;
	private Boolean active;
	private Double fee;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	private CourseResponse(final CourseResponseBuilder courseResponseBuilder){
		  this.id = courseResponseBuilder.id;
		  this.courseId = courseResponseBuilder.courseId;
		  this.title = courseResponseBuilder.title;
		  this.description = courseResponseBuilder.description;
		  this.active = courseResponseBuilder.active;
		  this.fee = courseResponseBuilder.fee;
	}
	
	public static CourseResponseBuilder builder(){
        return new CourseResponseBuilder();
    }
	public static class CourseResponseBuilder {
		private String id;
		private Integer courseId;
		private String title;
		private String description;
		private Boolean active;
		private Double fee;
		
		private CourseResponseBuilder(){}
		 
		 public CourseResponseBuilder withId(final String id) {
	            this.id = id;
	            return this;
	     }
		 public CourseResponseBuilder withCourseId(final Integer courseId) {
	            this.courseId = courseId;
	            return this;
	     }
		 public CourseResponseBuilder withTitle(final String title) {
	            this.title = title;
	            return this;
	     }
		 public CourseResponseBuilder withDescription(final String description) {
	            this.description = description;
	            return this;
	     }
		 public CourseResponseBuilder withActive(final Boolean active) {
	            this.active = active;
	            return this;
	     }
		 public CourseResponseBuilder withFee(final Double fee) {
	            this.fee = fee;
	            return this;
	     }
		 
		 public CourseResponse build(){
	            if(StringUtils.isEmpty(title) || StringUtils.isEmpty(description) || StringUtils.isEmpty(active)  || StringUtils.isEmpty(fee) ){
	                throw new IllegalArgumentException(ErrorMessageConstants.ADDRESS_RESPONSE_EMPTY);
	            }
	            return new CourseResponse(this);
	     }
	}
}
