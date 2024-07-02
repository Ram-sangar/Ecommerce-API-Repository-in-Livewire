package com.SpringBoot.EcommerceSiteProject.Payment.model;

import com.SpringBoot.EcommerceSiteProject.User.model.User;
import com.SpringBoot.EcommerceSiteProject.Order.model.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

   // @OneToMany(mappedBy = "payment")
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private String paymentMethod;

    private Double totalAmountWithGST ;


}
