package com.CourseWorld.CourseWorld.Component;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "img")
    private String img;

    @Column(name = "syllabus_pdf")
    private String syllabusPdf;
    
    @Column(name = "Language",nullable = false)
    private String Language;
    
    @Column(name = "Level",nullable = false)
    private String Level;

    // ✅ Many-to-One: Each Course is added by an Institute (User with role = Institute)
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User institute;

    // ✅ Many-to-One: Each Course belongs to a CourseCategory
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId", nullable = false)
    private CourseCategory courseCategory;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructors
    public Course() {}

	public Course(Long courseId, String name, String description, double price, String img, String syllabusPdf,
			String language, String level, User institute, CourseCategory courseCategory, LocalDateTime createdAt,
			LocalDateTime updatedAt, LocalDateTime deletedAt) {
		this.courseId = courseId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.img = img;
		this.syllabusPdf = syllabusPdf;
		Language = language;
		Level = level;
		this.institute = institute;
		this.courseCategory = courseCategory;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSyllabusPdf() {
		return syllabusPdf;
	}

	public void setSyllabusPdf(String syllabusPdf) {
		this.syllabusPdf = syllabusPdf;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getLevel() {
		return Level;
	}

	public void setLevel(String level) {
		Level = level;
	}

	public User getInstitute() {
		return institute;
	}

	public void setInstitute(User institute) {
		this.institute = institute;
	}

	public CourseCategory getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(CourseCategory courseCategory) {
		this.courseCategory = courseCategory;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}
}
