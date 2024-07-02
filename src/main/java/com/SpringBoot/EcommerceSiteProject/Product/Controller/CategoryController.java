package com.SpringBoot.EcommerceSiteProject.Product.Controller;

import com.SpringBoot.EcommerceSiteProject.Product.model.Category;
import com.SpringBoot.EcommerceSiteProject.Product.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory() {
        List<Category> categoryList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
       return categoryService.saveCategory(category);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer id, @RequestBody Category Category) {
        Optional<Category> category = categoryService.getCategoryById(id);
            if(category.isPresent()){

                Category updatedCategory = category.get();
                updatedCategory.setName(Category.getName());
                updatedCategory.setDescription(Category.getDescription());
                return ResponseEntity.ok(categoryService.saveCategory(updatedCategory));
            }else{
                return ResponseEntity.notFound().build();
            }
        }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete success");
    }
}

