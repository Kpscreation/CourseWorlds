package com.CourseWorld.CourseWorld.Component;

import jakarta.persistence.*;

@Entity
@Table(name="enrollment")
public class Enrollment {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	
	private int user_id;
	 
	 
	private int courseId;

	public Enrollment() {
		 
	}

	public Enrollment(Long id, int user_id, int courseId) {
		this.id = id;
		this.user_id = user_id;
		this.courseId = courseId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", user_id=" + user_id + ", courseId=" + courseId + "]";
	}
	
}
