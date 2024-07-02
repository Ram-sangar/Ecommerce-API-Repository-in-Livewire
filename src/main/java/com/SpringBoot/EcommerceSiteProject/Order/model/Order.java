package com.SpringBoot.EcommerceSiteProject.Order.model;

import com.SpringBoot.EcommerceSiteProject.Payment.model.Payment;
import com.SpringBoot.EcommerceSiteProject.User.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id",nullable=false)
    private User user;

    private Double orderTotal;

    @Enumerated(EnumType.STRING)
    private EOrderStatus orderStatus;

    private Double gstAmount;

    private Double totalAmountWithGST ;

    @JsonIgnore
    @OneToMany(mappedBy = "order",cascade = { CascadeType.PERSIST,CascadeType.MERGE},orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();


}
