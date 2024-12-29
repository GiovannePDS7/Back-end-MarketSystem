package com.MarketSystem.controller;

import com.MarketSystem.models.Category;
import com.MarketSystem.models.Section;
import com.MarketSystem.services.CategoryService;
import com.MarketSystem.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SectionService sectionService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        if (category.getSection() != null) {
            Section section = sectionService.getSectionById(category.getSection().getId()).orElse(null);
            if (section != null) {
                category.setSection(section);
            }
        }
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category updatedCategory) {
        return categoryService.getCategoryById(id)
                .map(existingCategory -> {
                    existingCategory.setName(updatedCategory.getName());
                    if (updatedCategory.getSection() != null) {
                        Section section = sectionService.getSectionById(updatedCategory.getSection().getId()).orElse(null);
                        if (section != null) {
                            existingCategory.setSection(section);
                        }
                    }
                    Category savedCategory = categoryService.saveCategory(existingCategory);
                    return ResponseEntity.ok(savedCategory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable String id) {
        if (categoryService.getCategoryById(id).isPresent()) {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
