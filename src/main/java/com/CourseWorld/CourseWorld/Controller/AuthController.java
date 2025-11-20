package com.CourseWorld.CourseWorld.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import com.CourseWorld.CourseWorld.Component.User;
import com.CourseWorld.CourseWorld.Service.UserService;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/login")
    public String viewLogin() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model, HttpSession session) {
        try {
            User authenticatedUser = userService.authenticate(user.getEmail(), user.getPassword());
            session.setAttribute("user", authenticatedUser);
            session.setAttribute("userRole", authenticatedUser.getRole().toString());
            session.setAttribute("userEmail", authenticatedUser.getEmail());
            session.setAttribute("userName", authenticatedUser.getName());
            session.setAttribute("userId", authenticatedUser.getUser_id());
            
            switch (authenticatedUser.getRole()) {
                case Institute:
                    return "redirect:/Institute";
                case User:
                    return "redirect:/User";
                case Admin:
                    return "redirect:/Admin";
                default:
                    return "redirect:/";
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/registration")
    public String viewRegistration() {
        return "registration";
    }
    
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Initialize error messages
        String nameError = null;
        String emailError = null;
        String phoneError = null;
        String passwordError = null;
        String roleError = null;

        // Validate fields
        if (userService.emailExists(user.getEmail())) {
            emailError = "Email is already registered.";
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

        if (user.getRole() == null) {
            roleError = "Please select a role.";
        }

        // Add error messages to model if any
        if (nameError != null || emailError != null || phoneError != null || passwordError != null || roleError != null) {
            model.addAttribute("nameError", nameError);
            model.addAttribute("emailError", emailError);
            model.addAttribute("phoneError", phoneError);
            model.addAttribute("passwordError", passwordError);
            model.addAttribute("roleError", roleError);
            return "registration";
        }

        // If no errors, proceed with registration
        userService.registerUser(user);
        model.addAttribute("successMessage", "A verification email has been sent. Please check your inbox.");
        return "registration";
    }
  
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("token") String token, Model model) {
        if (userService.verifyUser(token)) {
            model.addAttribute("successMessage", "Your email has been verified! You can now log in.");
        } else {
            model.addAttribute("errorMessage", "Invalid or expired verification link.");
        }
        return "varify";
    }
    
    // Show Forgot Password Page
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }
    
    // Handle Forgot Password Request (Send Email)
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            userService.sendPasswordResetEmail(email);
            model.addAttribute("successMessage", "A password reset link has been sent to your email.");
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Email not found. Please try again.");
        }
        return "forgot-password";
    }
    
    // Show Password Reset Form
    @GetMapping("/password-reset")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("errorMessage", "Invalid or expired token.");
            return "forgot-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }
    
    // Handle Password Reset Submission
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("newPassword") String newPassword,
                                Model model) {
        try {
            if (newPassword.length() < 6) {
                throw new IllegalArgumentException("Password must be at least 6 characters long.");
            }
            userService.resetPassword(token, newPassword);
            model.addAttribute("successMessage", "Password reset successful! You can now log in.");
            return "login";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "reset-password";
        }
    }
}