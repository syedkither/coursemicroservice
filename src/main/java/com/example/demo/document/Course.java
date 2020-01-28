package com.example.demo.document;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "course")
public class Course {
	@Id
	private String id;
	@NotNull(message = "Course ID must not be empty")
	private Integer courseId;
	@NotEmpty(message = "Course Title must not be empty")
	private String title;
	@NotEmpty(message = "Course Description must not be empty")
	private String description;
	@AssertTrue
	@NotNull(message = "Course Active flag must not be empty")
	private Boolean active;
	@NotNull(message = "Course Fee must not be empty")
	private Double fee;

	public Course() {

	}

	public Course(String title, String description, Boolean active, Integer courseId, Double fee) {
		this.title = title;
		this.description = description;
		this.active = active;
		this.courseId = courseId;
		this.fee = fee;
	}

	public String getId() {
		return id;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
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

	@Override
	public String toString() {
		return "Course{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", fee="
				+ fee + ", active=" + active + '}';
	}

}

/*
 * sample format in mongo atlas
 * {"_id":{"$oid":"5de7f3081c9d4400001632d2"},"courseId":"4","title":"AWS",
 * "description":"Amazon Web Service","active":"true"}
 * 
 * 
 * 
 * 
 */
