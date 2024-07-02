package com.SpringBoot.EcommerceSiteProject.Order.Repository;

import com.SpringBoot.EcommerceSiteProject.Order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByUserIdAndId(Long userId, Integer orderId);
}
