package com.example.demo.document;

public class CourseVO {

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
	@Override
	public String toString() {
		return "Course{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", fee="
				+ fee + ", active=" + active + '}';
	}
	
}
