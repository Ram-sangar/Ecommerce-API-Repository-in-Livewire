package com.SpringBoot.EcommerceSiteProject.Order.Repository;

import com.SpringBoot.EcommerceSiteProject.Order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
