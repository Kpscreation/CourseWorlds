package com.CourseWorld.CourseWorld.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CourseWorld.CourseWorld.Component.CourseCategory;
import com.CourseWorld.CourseWorld.Repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CourseCategory> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(CourseCategory category) {
        Optional<CourseCategory> existingCategory = 
            categoryRepository.findByCategoryName(category.getCategoryName());

        // Only throw an exception if the existing category has a different ID (to allow updates)
        if (existingCategory.isPresent() && !existingCategory.get().getCategoryId().equals(category.getCategoryId())) {
            throw new RuntimeException("Category already exists!");
        }

        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }
	
	public Optional<CourseCategory> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
	
	public List<CourseCategory> getActiveCategories() {
        return categoryRepository.findByDeletedAtIsNull();
    }
}

