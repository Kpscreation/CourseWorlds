package com.CourseWorld.CourseWorld.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.CourseWorld.CourseWorld.Component.Course;
import com.CourseWorld.CourseWorld.Component.CourseVideo;
import com.CourseWorld.CourseWorld.Component.User;
import com.CourseWorld.CourseWorld.Component.Contact;
import com.CourseWorld.CourseWorld.Repository.ContactRepository;
import com.CourseWorld.CourseWorld.Repository.CourseVideoRepository;
import com.CourseWorld.CourseWorld.Repository.EnrollmentRepository;
import com.CourseWorld.CourseWorld.Repository.UserRepository;
import com.CourseWorld.CourseWorld.Service.CategoryService;
import com.CourseWorld.CourseWorld.Service.CourseService;
import com.CourseWorld.CourseWorld.Service.FileStorageService;
import com.CourseWorld.CourseWorld.Service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ViewController {
    

    private final PasswordEncoder passwordEncoder;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	CourseVideoRepository courseVideoRepository;
	
	@Autowired
	ContactRepository contactRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
   
	
	@Autowired
	FileStorageService  filestorageService;


    ViewController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
	
	
	@GetMapping("/")
	public String viewIndex(Model model) {
		model.addAttribute("courses", courseService.getActiveCourses());
        model.addAttribute("categories", categoryService.getActiveCategories());
		return "index";
	}

	@GetMapping("/courses")
	public String showActiveCourses(Model model) {
        model.addAttribute("courses", courseService.getActiveCourses());
        model.addAttribute("categories", categoryService.getActiveCategories());
        return "courses"; // Thymeleaf template
    }
	
	@GetMapping("/courseDetail/{courseId}")
	public String showCourseDetails(@PathVariable Long courseId, Model model) {
	    Course course = courseService.getCourseById(courseId);
	    
	    if (course == null) {
	        return "error"; // Redirect to an error page if the course is not found or inactive
	    }
	    
	    model.addAttribute("course", course);
	    return "course_details"; // Thymeleaf template
	}

	@GetMapping("/contact")
	public String showContactPage()
	{
		return "contact";
	}

	@GetMapping("/about")
	public String showAboutPage()
	{
		return "about";
	}
	
	@GetMapping("/Admin")
    public String viewAdminIndex() {
        return "Admin/index";
    }
	
	@GetMapping("/User")
	public String viewUserPanel() {
		return "Admin/index";
	}
	
	@GetMapping("/Institute")
	public String viewInstitutePanel() {
		return "Admin/index";
	}
	
	@GetMapping("/access-denied")
	public String access_denied() {
		return "access_denied";
	}
	
	 @GetMapping("/User/enrolled")
	    public String showEnrolledCourses(Model model,HttpSession session) {
		 	Long userId = (Long) session.getAttribute("userId");
	        List<Course> courses = enrollmentRepository.findCoursesByUserId(userId);
	        model.addAttribute("courses", courses);
	        return "Admin/EnrolledCourses"; // Thymeleaf template name
	    }
	 
	 @GetMapping("User/enrolledCourseVideos/{courseId}")
	 public String showEnrolledCourseVideos(Model model,@PathVariable Long courseId) {
		 List<CourseVideo> videos = courseVideoRepository.findByCourse_CourseId(courseId);
		 model.addAttribute("videos", videos);
		 return "Admin/EnrolledCourseVideos";
	 }
	 
	 
	 
	 @PostMapping("/contact")
	 public String Contact(Model model,@ModelAttribute("contact") Contact contact, BindingResult bindResult)
	 {
		if(contact.getName() == null || contact.getName() == "")
		{
			model.addAttribute("name","Please Enter The Name");
		}
		else if(contact.getEmail() == null || contact.getEmail() == "")
		{
			model.addAttribute("email","Please Enter The Email");
		}
		else if(contact.getSubject() == null || contact.getSubject() == "")
		{
			model.addAttribute("subject","Please Enter The Subject");
		}
		else if(contact.getMessage() == null || contact.getMessage() == "")
		{
			model.addAttribute("message","Please Enter The Message");
		}
		else
		{
			contact.setCreatedAt(LocalDateTime.now());
			contactRepository.save(contact);		
			model.addAttribute("Success","Message Successfully Send");
		}				
		return "/contact";
	 }
	 
	   @GetMapping("User/profile")
		public String profile(HttpSession session,Model model)
		{
		   	Long userId = (Long) session.getAttribute("userId");
		   	User u1 = userRepository.findById(userId).orElseThrow();
		   	
		   	if(u1 == null)		   	
		   	{		   		
		   	  model.addAttribute("Error","userNotFound");
			  return "/Admin/profile";
		   	}
		   	else
		   	{		   		
		   		model.addAttribute("user",u1);
		   		return "/Admin/profile";
		   	}
		   	
		}
	   @GetMapping("Institute/profile")
		public String Instituteprofile()
		{
			 return "/Admin/profile";
		}
	   @GetMapping("Admin/profile")
		public String Adminprofile()
		{
			 return "/Admin/profile";
		}
	   
	   @PostMapping("/User/profile")
	   public String SendProfile(@ModelAttribute User user, Model model, HttpSession session,  @RequestParam("img") MultipartFile img)
	   {
		
		// Initialize error messages
	        String nameError = null;
	        String emailError = null;
	        String phoneError = null;
	        String passwordError = null;
	        String addressError = null;
	        String imgError = null;
	        
	        Long userId = (Long) session.getAttribute("userId");
		   	User u1 = userRepository.findById(userId).orElseThrow();

		   	System.out.println(img);
		   	
	        // Validate fields		   	
		   	if(!(u1.getEmail().equals(user.getEmail())))
		   	{
		   		System.out.println(u1.getEmail());
		   		System.out.println(user.getEmail());
		   		
		   		if (userService.emailExists(user.getEmail())) {
		   			emailError = "Email is already registered.";
		   		}
		   	}

	        if (user.getName() == null || user.getName().isEmpty() || !user.getName().matches("[a-zA-Z\\s]{2,50}")) {
	            nameError = "Name should be between 2 to 50 characters and contain only letters and spaces.";
	        }

	        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
	            emailError = emailError != null ? emailError : "Invalid email address.";
	        }

	        if (user.getPhone() == 0 || !String.valueOf(user.getPhone()).matches("[0-9]{10}")) {
	            phoneError = "Phone number should be exactly 10 digits.";
	        }

	        if (user.getPassword() == null || user.getPassword().isEmpty() || !user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
	            passwordError = "Password must contain at least one lowercase letter, one uppercase letter, one number, one special character, and be at least 8 characters long.";
	        }
	        
	        if (user.getAddress() == null || user.getAddress().isEmpty()) {
	        	addressError = "Please Enter Your Address";
	        }
	        
	        if (img != null && !img.isEmpty()) {
		        String imgContentType = img.getContentType();
		        if (!imgContentType.equals("image/jpeg") && !imgContentType.equals("image/jpg") && !imgContentType.equals("image/png") && !imgContentType.equals("image/webp")) {
		            imgError = "Only JPG, JPEG, and PNG image formats are supported.";
		        }
		    }	        	        

	        // Add error messages to model if any
	        if (nameError != null || emailError != null || phoneError != null || passwordError != null || addressError != null) {
	            model.addAttribute("nameError", nameError);
	            model.addAttribute("emailError", emailError);
	            model.addAttribute("phoneError", phoneError);
	            model.addAttribute("passwordError", passwordError);
	            model.addAttribute("addressError", addressError);
	            model.addAttribute("imgError", imgError);
	            return "/Admin/profile";
	        }

	        if (img != null && !img.isEmpty())
	        {
	        	filestorageService.saveFile(img);
	        }
	        else
	        {
	        	user.setImg(u1.getImg());
	        }
	        
	        user.setRole(u1.getRole());
	        user.setAdminVerified(u1.getAdminVerified());
	        user.setCreated_at(u1.getCreated_at());
	        user.setEnabled(u1.isEnabled());
	        user.setUpdated_at(LocalDateTime.now());
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        userRepository.save(user);
	        model.addAttribute("successMessage", "Successfully Updated.");	        		  
		   return "/Admin/profile";
	   }
	   
	   
}
