package com.CourseWorld.CourseWorld.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CourseWorld.CourseWorld.Component.Course;
import com.CourseWorld.CourseWorld.Repository.CourseRepository;

@Service
public class CourseService {
    
    @Autowired
    private CourseRepository courseRepository;
    
    public List<Course> getActiveCourses() {
        return courseRepository.findActiveCourses();
    }
    
    public Course getCourseById(Long courseId) {
        return courseRepository.findActiveCourseById(courseId).orElse(null);
    }
}

