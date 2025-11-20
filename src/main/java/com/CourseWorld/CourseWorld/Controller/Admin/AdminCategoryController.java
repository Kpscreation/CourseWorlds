package com.CourseWorld.CourseWorld.Controller.Admin;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.CourseWorld.CourseWorld.Component.CourseCategory;
import com.CourseWorld.CourseWorld.Service.CategoryService;

@Controller
@RequestMapping("/Admin/Category")
public class AdminCategoryController {

	@Autowired
	CategoryService categoryService;
	
	// Handle form submission
    @PostMapping("/add")
    public String addCategory(@ModelAttribute CourseCategory category,Model model) {
    	try {
            categoryService.saveCategory(category);
            return "redirect:/Admin/Category";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage()); // Pass error message to Thymeleaf
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("category", new CourseCategory()); // Reset form
            return "Admin/category"; // Return to the same page with the error message
        }
    }
    
 // Edit category by ID
    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        Optional<CourseCategory> categoryOpt = categoryService.getCategoryById(id);
        if (categoryOpt.isPresent()) {
            model.addAttribute("category", categoryOpt.get());
            return "Admin/categoryForm";
        } else {
            model.addAttribute("errorMessage", "Category not found!");
            return "redirect:/Admin/Category";
        }
    }
    
 // Toggle active/inactive status
    @GetMapping("/toggleStatus/{id}")
    public String toggleCategoryStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<CourseCategory> categoryOpt = categoryService.getCategoryById(id);
        if (categoryOpt.isPresent()) {
            CourseCategory category = categoryOpt.get();
            if (category.getDeletedAt() == null) {
                category.setDeletedAt(LocalDateTime.now()); // Deactivate
                redirectAttributes.addFlashAttribute("successMessage", "Category deactivated successfully.");
            } else {
                category.setDeletedAt(null); // Activate
                redirectAttributes.addFlashAttribute("successMessage", "Category activated successfully.");
            }
            categoryService.saveCategory(category);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Category not found.");
        }
        return "redirect:/Admin/Category";
    }
}
