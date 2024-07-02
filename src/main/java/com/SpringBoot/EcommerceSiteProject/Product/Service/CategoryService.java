package com.SpringBoot.EcommerceSiteProject.Product.Service;

import com.SpringBoot.EcommerceSiteProject.Product.model.Category;
import com.SpringBoot.EcommerceSiteProject.Product.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    public Category getCategory(Integer id){
        Optional<Category> mayBeCategory = categoryRepository.findById(id);
        return mayBeCategory.orElse(null);
    }

    public Optional<Category> getCategoryById(Integer id){
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public void deleteCategory (Integer id){
         categoryRepository.deleteById(id);


    }

    /*public void updateCategory(Integer categoryID,Category newCategory){
        Category category= categoryRepository.findById(categoryID).getById();
        category.setCategory_name(newCategory.getCategory_name());
        category.setDescription(newCategory.getDescription());
        categoryRepository.save((category));

    }*/
}
