package com.SpringBoot.EcommerceSiteProject.Product.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Category_name")
    private String name;

    private String description;

   /* @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Product> products= new ArrayList<>();*/
}
