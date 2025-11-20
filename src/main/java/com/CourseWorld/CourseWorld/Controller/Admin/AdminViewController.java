package com.CourseWorld.CourseWorld.Controller.Admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.CourseWorld.CourseWorld.Component.CourseCategory;
import com.CourseWorld.CourseWorld.Component.User;
import com.CourseWorld.CourseWorld.Repository.UserRepository;
import com.CourseWorld.CourseWorld.Service.CategoryService;
import com.CourseWorld.CourseWorld.Service.UserService;

@Controller
@RequestMapping("/Admin")
public class AdminViewController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private UserService userService;    
    
    @GetMapping("/Category")
    public String viewCategory(Model model) {
        List<CourseCategory> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("category", new CourseCategory()); // ✅ Add this
        return "Admin/Category"; // Loads Admin/category.html with categories
    }
    
    @GetMapping("/Category/add")
    public String viewAddCategoryForm(Model model) {
        model.addAttribute("category", new CourseCategory()); // Ensure this is set
        return "Admin/CategoryForm";
    }
    
    @GetMapping("/Institute")
    public String showInstituteAccounts(Model model) {
        List<User> institutes = userService.getAllInstitutes();
        model.addAttribute("institutes", institutes);
        return "Admin/Institute"; // This will be the Thymeleaf template name
    }
    
    @GetMapping("/Users")
    public String showUserAccounts(Model model) {
        List<User> users = userService.getAllUsers(); // Fetch all users
        model.addAttribute("users", users);
        return "Admin/Users";// Thymeleaf template name
    }
    
    @PostMapping("/VerifyInstitute")
    public String verifyOrBlockInstitute(@RequestParam long userId, RedirectAttributes redirectAttributes) {
        // Fetch the institute by userId
        Optional<User> instituteOptional = userRepository.findById(userId);

        if (instituteOptional.isPresent()) {
            User institute = instituteOptional.get();

            // Toggle verification status (true = Verified, false = Blocked)
            if (institute.isAdminVerified()) {
                institute.setAdminVerified(false); // Block institute
                redirectAttributes.addFlashAttribute("message", "Institute has been blocked.");
            } else {
                institute.setAdminVerified(true); // Verify institute
                redirectAttributes.addFlashAttribute("message", "Institute has been verified.");
            }

            // Save updated status
            userRepository.save(institute);
        } else {
            redirectAttributes.addFlashAttribute("message", "Institute not found.");
        }

        return "redirect:/Admin/Institute";
    }
    
    @PostMapping("/VerifyUser")
    public String verifyOrBlockUser(@RequestParam long userId, RedirectAttributes redirectAttributes) {
        // Fetch the user by userId
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Toggle verification status (true = Verified, false = Blocked)
            if (user.isAdminVerified()) {
                user.setAdminVerified(false); // Block user
                redirectAttributes.addFlashAttribute("message", "User has been blocked.");
            } else {
                user.setAdminVerified(true); // Verify user
                redirectAttributes.addFlashAttribute("message", "User has been verified.");
            }

            // Save updated status
            userRepository.save(user);
        } else {
            redirectAttributes.addFlashAttribute("message", "User not found.");
        }

        return "redirect:/Admin/Users";
    }              
    
}
