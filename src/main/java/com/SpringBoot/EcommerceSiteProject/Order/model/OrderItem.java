package com.SpringBoot.EcommerceSiteProject.Order.model;

import com.SpringBoot.EcommerceSiteProject.Product.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    @Transient
    private Integer tempOrderId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    @Transient
    private Integer tempsProductId;

    private Integer quantity;
    private double gstAmount;
    private double productPrice;
    private double itemTotal;
}
