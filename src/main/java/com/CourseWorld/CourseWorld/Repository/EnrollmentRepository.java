package com.CourseWorld.CourseWorld.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.CourseWorld.CourseWorld.Component.Course;
import com.CourseWorld.CourseWorld.Component.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
	
	@Query(value = "SELECT c.* FROM course c " +
            "JOIN enrollment e ON c.course_id = e.course_id " +
            "WHERE e.user_id = :userId", nativeQuery = true)
		List<Course> findCoursesByUserId(@Param("userId") Long userId);
}
