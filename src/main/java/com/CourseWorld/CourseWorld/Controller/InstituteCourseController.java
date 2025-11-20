package com.CourseWorld.CourseWorld.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.CourseWorld.CourseWorld.Component.Course;
import com.CourseWorld.CourseWorld.Component.CourseCategory;
import com.CourseWorld.CourseWorld.Component.User;
import com.CourseWorld.CourseWorld.Component.User.Role;
import com.CourseWorld.CourseWorld.Repository.CategoryRepository;
import com.CourseWorld.CourseWorld.Repository.CourseRepository;
import com.CourseWorld.CourseWorld.Repository.UserRepository;
import com.CourseWorld.CourseWorld.Service.CategoryService;
import com.CourseWorld.CourseWorld.Service.FileStorageService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/Institute/Course")
public class InstituteCourseController {
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping
	public String ShowAllInstitutes(Model model, HttpSession session) {
	    // Retrieve the logged-in institute user from the session
	    User institute = (User) session.getAttribute("user");

	    if (institute == null) {
	        return "redirect:/login"; // Redirect to login if no institute is found in session
	    }

	    // Fetch courses added by the logged-in institute
	    List<Course> courses = courseRepository.findByInstitute(institute);
	    model.addAttribute("courses", courses);
		System.out.println();

	    return "Admin/InstituteCourses";
	}
	
	@GetMapping("/add")
	public String ShowAddCourseForm(Model model) {
		model.addAttribute("categories",categoryService.getAllCategories());
		return "Admin/AddCourse";
	}
	
	@PostMapping("/add")
	public String addCourse(
	    @RequestParam("name") String name,
	    @RequestParam("description") String description,
	    @RequestParam("price") double price,
	    @RequestParam("Language") String language,
	    @RequestParam("Level") String level,
	    @RequestParam("categoryId") Long categoryId,
	    @RequestParam("img") MultipartFile img,
	    @RequestParam("syllabusPdf") MultipartFile syllabusPdf,
	    HttpSession session,
	    Model model
	) {
	    
	    Long userId = (Long) session.getAttribute("userId");
	    
	    if (userId == null) {
	        throw new RuntimeException("Access Denied: No logged in user found.");
	    }
	    
	    // Fetch user from the database
	    User loggedInUser = userRepository.findById(userId)
	                                      .orElseThrow(() -> new RuntimeException("User not found"));
	    
	    // Validate role
	    if (loggedInUser.getRole() != Role.Institute) {
	        throw new RuntimeException("Access Denied: Only institutes can add courses.");
	    }
	    
	    // Initialize error messages
	    String nameError = null;
	    String descriptionError = null;
	    String priceError = null;
	    String languageError = null;
	    String levelError = null;
	    String imgError = null;
	    String syllabusPdfError = null;
	    String categoryError = null;

	    // Validate fields
	    if (name == null || name.isEmpty() || !name.matches("[a-zA-Z\\s]+") || name.length() > 100) {
	        nameError = "Name should not be empty, should only contain letters and spaces, and should be less than 100 characters.";
	    }

	    if (description == null || description.isEmpty() || description.length() > 2000) {
	        descriptionError = "Description should not be empty, should only contain letters and spaces, and should be less than 2000 characters.";
	    }

	    if (price <= 0) {
	        priceError = "Price should be greater than zero.";
	    }

	    if (language == null || language.isEmpty() || language.length() > 50) {
	        languageError = "Language should not be empty and should be less than 50 characters.";
	    }

	    if (level == null || level.isEmpty() || level.length() > 50) {
	        levelError = "Level should not be empty and should be less than 50 characters.";
	    }

	    if (img.isEmpty()) {
	        imgError = "Please select an image.";
	    } else {
	        String imgContentType = img.getContentType();
	        if (!imgContentType.equals("image/jpeg") && !imgContentType.equals("image/jpg") && !imgContentType.equals("image/png") && !imgContentType.equals("image/webp")) {
	            imgError = "Only JPG, JPEG, and PNG image formats are supported.";
	        }
	    }

	    if (syllabusPdf.isEmpty()) {
	        syllabusPdfError = "Please select a syllabus PDF.";
	    } else {
	        String pdfContentType = syllabusPdf.getContentType();
	        if (!pdfContentType.equals("application/pdf")) {
	            syllabusPdfError = "Only PDF files are supported for syllabus.";
	        }
	    }

	    if (categoryId == null || categoryId <= 0) {
	        categoryError = "Please select a category.";
	    }

	    // Add error messages to model if any
	    if (nameError != null || descriptionError != null || priceError != null || languageError != null || levelError != null || imgError != null || syllabusPdfError != null || categoryError != null) {
	        model.addAttribute("nameError", nameError);
	        model.addAttribute("descriptionError", descriptionError);
	        model.addAttribute("priceError", priceError);
	        model.addAttribute("languageError", languageError);
	        model.addAttribute("levelError", levelError);
	        model.addAttribute("imgError", imgError);
	        model.addAttribute("syllabusPdfError", syllabusPdfError);
	        model.addAttribute("categoryError", categoryError);
	        
	        // Fetch categories for dropdown
	        List<CourseCategory> categories = categoryRepository.findAll();
	        model.addAttribute("categories", categories);
	        
	        return "Admin/AddCourse"; // Return the form view with error messages
	    }

	    if (courseRepository.existsByNameAndInstitute(name, loggedInUser)) {
	        model.addAttribute("error", "A course with this name already exists.");
	        
	        // Fetch categories for dropdown
	        List<CourseCategory> categories = categoryRepository.findAll();
	        model.addAttribute("categories", categories);
	        
	        return "Admin/AddCourse"; // Return the form view with error message
	    }

	    CourseCategory category = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new RuntimeException("Category not found for id: " + categoryId));

	    Course course = new Course();
	    course.setName(name);
	    course.setDescription(description);
	    course.setPrice(price);
	    course.setLanguage(language);
	    course.setLevel(level);
	    course.setCourseCategory(category);

	    // Save file names
	    String imgFileName = fileStorageService.saveFile(img);
	    String pdfFileName = fileStorageService.saveFile(syllabusPdf);

	    course.setImg(imgFileName);
	    course.setSyllabusPdf(pdfFileName);
	    course.setCreatedAt(LocalDateTime.now());
	    course.setUpdatedAt(LocalDateTime.now());
	    course.setInstitute(loggedInUser);

	    courseRepository.save(course);

	    return "redirect:/Institute/Course";
	}
	
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id,Model model) {
		
		Course course = courseRepository.findById(id).orElse(null);
		if (course == null) {
	        return "redirect:/Institute/Course"; // Redirect if course not found
	    }
		model.addAttribute("course", course); // Pass course object to Thymeleaf
	    model.addAttribute("categories", categoryRepository.findAll()); // Pass categories for dropdown
		return "Admin/EditCourse";
	}
	
	@PostMapping("/edit")
	public String editCourse(
	    @RequestParam("courseId") Long courseId,
	    @RequestParam("name") String name,
	    @RequestParam("description") String description,
	    @RequestParam("price") double price,
	    @RequestParam("Language") String language,
	    @RequestParam("Level") String level,
	    @RequestParam("categoryId") Long categoryId,
	    @RequestParam(value = "img", required = false) MultipartFile img,
	    @RequestParam(value = "syllabusPdf", required = false) MultipartFile syllabusPdf,
	    HttpSession session,
	    Model model
	) {
	    
	    Long userId = (Long) session.getAttribute("userId");
	    
	    if (userId == null) {
	        throw new RuntimeException("Access Denied: No logged in user found.");
	    }
	    
	    // Fetch user from the database
	    User loggedInUser = userRepository.findById(userId)
	                                      .orElseThrow(() -> new RuntimeException("User not found"));
	    
	    // Validate role
	    if (loggedInUser.getRole() != Role.Institute) {
	        throw new RuntimeException("Access Denied: Only institutes can edit courses.");
	    }
	    
	    // Initialize error messages
	    String nameError = null;
	    String descriptionError = null;
	    String priceError = null;
	    String languageError = null;
	    String levelError = null;
	    String imgError = null;
	    String syllabusPdfError = null;
	    String categoryError = null;

	    // Validate fields
	    if (name == null || name.isEmpty() || !name.matches("[a-zA-Z\\s]+") || name.length() > 100) {
	        nameError = "Name should not be empty, should only contain letters and spaces, and should be less than 100 characters.";
	    }

	    if (description == null || description.isEmpty() ||  description.length() > 2000) {
	        descriptionError = "Description should not be empty, should only contain letters and spaces, and should be less than 2000 characters.";
	    }

	    if (price <= 0) {
	        priceError = "Price should be greater than zero.";
	    }

	    if (language == null || language.isEmpty() || language.length() > 50) {
	        languageError = "Language should not be empty and should be less than 50 characters.";
	    }

	    if (level == null || level.isEmpty() || level.length() > 50) {
	        levelError = "Level should not be empty and should be less than 50 characters.";
	    }

	    if (img != null && !img.isEmpty()) {
	        String imgContentType = img.getContentType();
	        if (!imgContentType.equals("image/jpeg") && !imgContentType.equals("image/jpg") && !imgContentType.equals("image/png") && !imgContentType.equals("image/webp")) {
	            imgError = "Only JPG, JPEG, and PNG image formats are supported.";
	        }
	    }

	    if (syllabusPdf != null && !syllabusPdf.isEmpty()) {
	        String pdfContentType = syllabusPdf.getContentType();
	        if (!pdfContentType.equals("application/pdf")) {
	            syllabusPdfError = "Only PDF files are supported for syllabus.";
	        }
	    }

	    if (categoryId == null || categoryId <= 0) {
	        categoryError = "Please select a category.";
	    }

	    // Add error messages to model if any
	    if (nameError != null || descriptionError != null || priceError != null || languageError != null || levelError != null || imgError != null || syllabusPdfError != null || categoryError != null) {
	        model.addAttribute("nameError", nameError);
	        model.addAttribute("descriptionError", descriptionError);
	        model.addAttribute("priceError", priceError);
	        model.addAttribute("languageError", languageError);
	        model.addAttribute("levelError", levelError);
	        model.addAttribute("imgError", imgError);
	        model.addAttribute("syllabusPdfError", syllabusPdfError);
	        model.addAttribute("categoryError", categoryError);
	        
	        // Fetch categories for dropdown
	        List<CourseCategory> categories = categoryRepository.findAll();
	        model.addAttribute("categories", categories);
	        
	        // Fetch course details
	        Course course = courseRepository.findById(courseId)
	                                        .orElseThrow(() -> new RuntimeException("Course not found"));
	        model.addAttribute("course", course);
	        
	        return "Admin/EditCourse"; // Return the form view with error messages
	    }

	    // Check if course with the same name already exists for the institute, excluding the current course
	    if (courseRepository.existsByNameAndInstituteExcludingCurrent(name, loggedInUser, courseId)) {
	        model.addAttribute("error", "A course with this name already exists.");
	        
	        // Fetch categories for dropdown
	        List<CourseCategory> categories = categoryRepository.findAll();
	        model.addAttribute("categories", categories);
	        
	        // Fetch course details
	        Course course = courseRepository.findById(courseId)
	                                        .orElseThrow(() -> new RuntimeException("Course not found"));
	        model.addAttribute("course", course);
	        
	        return "Admin/EditCourse"; // Return the form view with error message
	    }

	    Course course = courseRepository.findById(courseId)
	                                    .orElseThrow(() -> new RuntimeException("Course not found"));
	    
	    CourseCategory category = categoryRepository.findById(categoryId)
	            .orElseThrow(() -> new RuntimeException("Category not found for id: " + categoryId));

	    course.setName(name);
	    course.setDescription(description);
	    course.setPrice(price);
	    course.setLanguage(language);
	    course.setLevel(level);
	    course.setCourseCategory(category);

	    if (img != null && !img.isEmpty()) {
	        String imgFileName = fileStorageService.saveFile(img);
	        course.setImg(imgFileName);
	    }

	    if (syllabusPdf != null && !syllabusPdf.isEmpty()) {
	        String pdfFileName = fileStorageService.saveFile(syllabusPdf);
	        course.setSyllabusPdf(pdfFileName);
	    }

	    course.setUpdatedAt(LocalDateTime.now());
	    course.setInstitute(loggedInUser);

	    courseRepository.save(course);

	    return "redirect:/Institute/Course";
	}
	
	@PostMapping("/toggleStatus/{courseId}")
	public String toggleCourseStatus(@PathVariable Long courseId, RedirectAttributes redirectAttributes) {
	    Course course = courseRepository.findById(courseId)
	        .orElseThrow(() -> new RuntimeException("Course not found"));

	    if (course.getDeletedAt() == null) {
	        course.setDeletedAt(LocalDateTime.now()); // Deactivate
	        redirectAttributes.addFlashAttribute("successMessage", "Course deactivated successfully.");
	    } else {
	        course.setDeletedAt(null); // Activate
	        redirectAttributes.addFlashAttribute("successMessage", "Course activated successfully.");
	    }

	    courseRepository.save(course);
	    return "redirect:/Institute/Course"; // Redirect back to course list
	}
}

