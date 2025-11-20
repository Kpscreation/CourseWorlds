package com.CourseWorld.CourseWorld.Component;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "course_category")
public class CourseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;  

    @Column(name = "category_name", nullable = false)
    private String categoryName;   

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; 

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at", nullable = true) // Allows NULL values for soft deletion
    private LocalDateTime deletedAt;
    
    public CourseCategory() {
    	
    }

	public CourseCategory(Long categoryId, String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt,
			LocalDateTime deletedAt) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
