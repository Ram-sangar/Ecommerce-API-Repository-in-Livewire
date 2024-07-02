package com.SpringBoot.EcommerceSiteProject.Order.Service;

import com.SpringBoot.EcommerceSiteProject.Cart.model.Cart;
import com.SpringBoot.EcommerceSiteProject.Cart.model.CartItem;
import com.SpringBoot.EcommerceSiteProject.Order.model.EOrderStatus;
import com.SpringBoot.EcommerceSiteProject.Order.model.Order;
import com.SpringBoot.EcommerceSiteProject.Order.model.OrderItem;
import com.SpringBoot.EcommerceSiteProject.Order.Repository.OrderItemRepository;
import com.SpringBoot.EcommerceSiteProject.Order.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    public Order createOrder(Cart cart) {
        Order order = Order.builder().orderStatus(EOrderStatus.PENDING).orderTotal(cart.getTotalPrice())
                .gstAmount(cart.getGstAmount())
                .totalAmountWithGST(cart.getTotalAmountWithGST()).user(cart.getUser()).build();

        orderRepository.save(order);

        //  Map Cart Items to Order Items
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem items : cart.getCartItem()) {
            OrderItem orderItem = OrderItem.builder().order(order).itemTotal(items.getItemTotal()).product(items.getProduct())
                    .quantity(items.getQuantity()).gstAmount(items.getGstAmount())
                    .productPrice(items.getProductPrice())
                    .build();

            orderItems.add(orderItem);
        }

        //  Save Order Items using order service
        orderItemRepository.saveAll(orderItems);
        return order;

    }

    public void saveOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);

    }

    public Order findOrder(Long userId, Integer orderId) throws Exception {
        Optional<Order> maybeOrder = orderRepository.findByUserIdAndId(userId, orderId);

        if(maybeOrder.isPresent()){
            return maybeOrder.get();
        }else{
            throw new Exception("Order not found");
        }

    }

    public void completeOrder(Order order){
        order.setOrderStatus(EOrderStatus.COMPLETED);
        orderRepository.save(order);
    }

    //public void updateOrder(Order order) {
      //  orderRepository.save(order);
   // }
}
