package com.CourseWorld.CourseWorld.Repository;

import com.CourseWorld.CourseWorld.Component.Course;
import com.CourseWorld.CourseWorld.Component.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // ✅ Fetch all courses added by a specific institute
    List<Course> findByInstitute(User institute);
    
    boolean existsByNameAndInstitute(String name, User institute);
    
 // Fetch only active courses where deletedAt is null and category is also active
    @Query("SELECT c FROM Course c JOIN c.courseCategory cat WHERE c.deletedAt IS NULL AND cat.deletedAt IS NULL")
    List<Course> findActiveCourses();
    
    @Query("SELECT c FROM Course c WHERE c.courseId = :courseId AND c.deletedAt IS NULL")
    Optional<Course> findActiveCourseById(@Param("courseId") Long courseId);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Course c WHERE c.name = :name AND c.institute = :institute AND c.courseId != :courseId")
    boolean existsByNameAndInstituteExcludingCurrent(@Param("name") String name, @Param("institute") User institute, @Param("courseId") Long courseId);
}
