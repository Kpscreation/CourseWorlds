package com.CourseWorld.CourseWorld.Controller;

import com.CourseWorld.CourseWorld.Service.CourseVideoService;
import com.CourseWorld.CourseWorld.Repository.CourseRepository;
import com.CourseWorld.CourseWorld.Repository.CourseVideoRepository;
import com.CourseWorld.CourseWorld.Component.Course;
import com.CourseWorld.CourseWorld.Component.CourseVideo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/Institute/CourseVideos")
public class InstituteCourseVideos {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseVideoService courseVideoService;
    
    @Autowired
    private CourseVideoRepository courseVideoRepository;

    // Show course videos page with course list
    @GetMapping
    public String showAllCourseVideos(Model model) {
        List<Course> courses = courseRepository.findAll();
        List<CourseVideo> videos = courseVideoRepository.findAll(); // Fetch videos

        model.addAttribute("courses", courses);
        model.addAttribute("videos", videos); // Pass videos to Thymeleaf

        return "Admin/CourseVideos"; // Ensure this maps to your Thymeleaf template
    }

    @PostMapping("/uploadVideo")
    public String uploadVideo(@RequestParam("courseId") Long courseId,
                              @RequestParam("videoName") String videoName,
                              @RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes) {
        try {
            String response = courseVideoService.uploadVideo(courseId, videoName, file);
            redirectAttributes.addFlashAttribute("message", response); // Store success message
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Error uploading video: " + e.getMessage()); // Store error message
        }
        return "redirect:/Institute/CourseVideos"; // Redirect to the desired route
    }
    
    @PostMapping("/deleteVideo/{videoId}")
    public String deleteVideoById(@PathVariable Long videoId,RedirectAttributes redirectAttributes) {
    	courseVideoRepository.deleteById(videoId);
    	redirectAttributes.addFlashAttribute("message","video deleted successfully");
    	return "redirect:/Institute/CourseVideos";
    }
}
