package com.CourseWorld.CourseWorld.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CourseWorld.CourseWorld.Component.CourseVideo;

@Repository
public interface CourseVideoRepository extends JpaRepository<CourseVideo, Long> {
	
	List<CourseVideo> findByCourse_CourseId(Long courseId);
}
