package com.CourseWorld.CourseWorld.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CourseWorld.CourseWorld.Component.CourseCategory;

@Repository
public interface CategoryRepository extends JpaRepository<CourseCategory, Long> {
	Optional<CourseCategory> findByCategoryName(String categoryName);
	
	 List<CourseCategory> findByDeletedAtIsNull(); // Fetch only active categories
}
