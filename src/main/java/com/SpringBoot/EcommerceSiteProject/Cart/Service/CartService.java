package com.SpringBoot.EcommerceSiteProject.Cart.Service;

import com.SpringBoot.EcommerceSiteProject.Cart.Repository.CartItemRepository;
import com.SpringBoot.EcommerceSiteProject.Cart.Repository.CartRepository;
import com.SpringBoot.EcommerceSiteProject.Cart.model.Cart;
import com.SpringBoot.EcommerceSiteProject.Cart.model.CartItem;
import com.SpringBoot.EcommerceSiteProject.Cart.model.CartDTO;
import com.SpringBoot.EcommerceSiteProject.Configuration.UserContext;
import com.SpringBoot.EcommerceSiteProject.Order.Repository.OrderItemRepository;
import com.SpringBoot.EcommerceSiteProject.Order.Service.OrderService;
import com.SpringBoot.EcommerceSiteProject.Order.model.Order;
import com.SpringBoot.EcommerceSiteProject.Payment.Service.PaymentService;
import com.SpringBoot.EcommerceSiteProject.Product.Repository.ProductRepository;
import com.SpringBoot.EcommerceSiteProject.Product.model.Product;
import com.SpringBoot.EcommerceSiteProject.User.model.User;
import com.SpringBoot.EcommerceSiteProject.User.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private OrderService orderService;

    @Transactional
    public void addToCart(CartDTO cartDTO) throws Exception {

        Long userId = UserContext.getUserId();

        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User does not exists"));

        Cart cart = cartRepository.findByUserId(userId).orElse(new Cart());

        cart.setUser(user);

        cart = cartRepository.save(cart);

        Product product = productRepository.findById(cartDTO.getCartItem().getTempProductId()).orElseThrow(() -> new Exception("Product does not exists"));

        //CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).orElse(new CartItem());

        int quantity = cartDTO.getCartItem().getQuantity();
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setProductPrice(product.getPrice());
        calculateTotal(cartItem, cart, product);

        cartRepository.save(cart);

    }

    public Cart calculateTotal(CartItem cartItem, Cart cart, Product product) {

        double itemTotal = cartItem.getQuantity() * product.getPrice();
        double gstAmount = itemTotal * (product.getGstPercentage() / 100);
        cartItem.setItemTotal(itemTotal);
        cartItem.setGstAmount(gstAmount);

        cartItemRepository.save(cartItem);

        List<CartItem> cartItemList = cartItemRepository.findByCartId(cart.getId());

        double finalTotal = cartItemList.stream().mapToDouble(item -> item.getItemTotal()).sum();
        double gstTotal = cartItemList.stream().mapToDouble(item -> item.getGstAmount()).sum();
        double finalTotalWithGst = finalTotal + gstTotal;

        cart.setTotalPrice(finalTotal);
        cart.setGstAmount(gstTotal);
        cart.setTotalAmountWithGST(finalTotalWithGst);

        return cart;
    }

    public Cart getCartById(Integer id) throws Exception {
        Optional<Cart> cart = cartRepository.findById(id);
        return cart.orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart updateCart(Integer cartId, CartItem cartItem) throws Exception {
        Cart cart = getCartById(cartId);

        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(),
                cartItem.getTempProductId()).orElseThrow(() -> new Exception("cart item not found"));

        Product product = productRepository.findById(cartItem.getTempProductId())
                .orElseThrow(() -> new Exception("Product not found"));

        existingItem.setQuantity(cartItem.getQuantity());
        existingItem.setProductPrice(product.getPrice());

        calculateTotal(existingItem, cart, product);

        return cartRepository.save(cart);
    }

    @Transactional
    public void deleteCartItem(Integer cartItemId) throws Exception {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow( () -> new Exception("Cart Item Not Found"));

        Cart cart = cartRepository.findById(cartItem.getCart().getId()).orElseThrow( () -> new Exception("Cart Not Found"));

        cartItemRepository.deleteOrderItemById(cartItemId);

        System.out.println("Cart item deleted successfully");

        List<CartItem> existingItems = cartItemRepository.findByCartId(cart.getId());

        double finalTotal = existingItems.stream().mapToDouble(item -> item.getItemTotal()).sum();
        double gstTotal = existingItems.stream().mapToDouble(item -> item.getGstAmount()).sum();
        double finalTotalWithGst = finalTotal + gstTotal;

        cart.setTotalPrice(finalTotal);
        cart.setGstAmount(gstTotal);
        cart.setTotalAmountWithGST(finalTotalWithGst);
        cartRepository.save(cart);

    }

    @Transactional
    public void createOrder(Long userId) throws Exception {

        //   Find the cart by user ID
         Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new Exception("User does not exists"));

        //  Create the order

         Order order = orderService.createOrder(cart);

         cart.setOrderId(order.getId());

         cartRepository.save(cart);

         System.out.println("order completed successfully");



        // cartRepository.delete(cart);



        // Step 1 - Create Order

        //Step 2 - Create payment(order)

        //Step 3 - Update order to completed

        //Step 4 - Delete Cart Items


    }

    public void deleteCart(Long userId) throws Exception {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow( () -> new Exception("Cart Not Found"));
        cartRepository.delete(cart);

    }
}












