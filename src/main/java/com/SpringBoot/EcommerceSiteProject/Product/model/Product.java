package com.SpringBoot.EcommerceSiteProject.Product.model;

import com.SpringBoot.EcommerceSiteProject.Cart.model.CartItem;
import com.SpringBoot.EcommerceSiteProject.User.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    private  String product_Name;
    private  String description;
    private  double price;
    private double gstPercentage;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id",nullable = false)
    private Category category;

    @Transient
    private Integer tempCategoryId;

    /*@OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    private List<CartItem> cartItems = new ArrayList<>();*/

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
