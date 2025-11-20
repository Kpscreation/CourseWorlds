package com.CourseWorld.CourseWorld.Service;

import com.CourseWorld.CourseWorld.Component.Course;
import com.CourseWorld.CourseWorld.Component.CourseVideo;
import com.CourseWorld.CourseWorld.Repository.CourseRepository;
import com.CourseWorld.CourseWorld.Repository.CourseVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CourseVideoService {

	private String uploadDir = "D:/sts projects/CourseWorld/uploads/videos/";

    @Autowired
    private CourseVideoRepository courseVideoRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String uploadVideo(Long courseId, String videoName, MultipartFile file) throws IOException {
        // Validate course exists
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Validate file is not empty
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        // Generate a unique file name to avoid conflicts
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);
        
        System.out.println("Saving file to: " + filePath.toString());

        // Ensure upload directory exists
        Files.createDirectories(uploadPath);

        // Save the file to disk
        Files.copy(file.getInputStream(), filePath);

        // Save metadata in the database
        CourseVideo video = new CourseVideo();
        video.setCourse(course);
        video.setVideoName(videoName);
        video.setFileName(fileName);  // Saving file name, NOT file path
        courseVideoRepository.save(video);
        
        return "video uploaded successfully";
    }

    public List<CourseVideo> getVideosByCourse(Long courseId) {
        return courseVideoRepository.findByCourse_CourseId(courseId);
    }

    public String softDeleteVideo(Long videoId) {
        CourseVideo video = courseVideoRepository.findById(videoId)
                .orElseThrow(() -> new RuntimeException("Video not found"));

        video.softDelete();
        courseVideoRepository.save(video);
        return "Video soft deleted!";
    }
}

