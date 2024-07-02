package com.SpringBoot.EcommerceSiteProject.User.model;

import com.SpringBoot.EcommerceSiteProject.Address.model.Address;
import com.SpringBoot.EcommerceSiteProject.Cart.model.Cart;
import com.SpringBoot.EcommerceSiteProject.Product.model.Product;
import com.SpringBoot.EcommerceSiteProject.Order.model.Order;
import com.SpringBoot.EcommerceSiteProject.Payment.model.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String userName;
    private  String email;
    private  String password;

    @Enumerated(EnumType.STRING)
    private ERole role;

   /* @OneToMany(mappedBy = "user")
    private List<Address> addresses;*/

/*    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private Cart cart;*/

   /* @OneToMany(mappedBy = "user")
    private List<Product> products;*/


   /* @OneToMany(mappedBy = "user",cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
    private List<Order> order;*/

    /*@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Payment> payment = new ArrayList<>();*/

    public User(){

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
