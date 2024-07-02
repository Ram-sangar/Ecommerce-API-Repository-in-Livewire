package com.SpringBoot.EcommerceSiteProject.Product.Repository;

import com.SpringBoot.EcommerceSiteProject.Product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
