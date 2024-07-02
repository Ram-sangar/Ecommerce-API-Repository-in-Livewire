package com.SpringBoot.EcommerceSiteProject.Cart.Controller;

import com.SpringBoot.EcommerceSiteProject.Cart.Service.CartService;
import com.SpringBoot.EcommerceSiteProject.Cart.model.Cart;
import com.SpringBoot.EcommerceSiteProject.Cart.model.CartDTO;
import com.SpringBoot.EcommerceSiteProject.Cart.model.CartItem;
import com.SpringBoot.EcommerceSiteProject.Configuration.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/cart")
public class CartController {

   @Autowired
    private CartService cartService;

    @PostMapping
    public String addToCart(@RequestBody CartDTO cartDTO) throws Exception {
        try {
            cartService.addToCart(cartDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", e);
        }

        return "success";
    }

    @GetMapping("/{id}")
    public Cart getCart(@PathVariable Integer id) throws Exception {
        return cartService.getCartById(id);
    }

    @PutMapping("/{id}")
    public Cart updateCart(@PathVariable Integer id, @RequestBody CartItem cartItem) throws Exception {
        return cartService.updateCart(id, cartItem);
    }

    @DeleteMapping("/item/{cartItemId}")
    public void deleteCartItem(@PathVariable Integer cartItemId) throws Exception {
         cartService.deleteCartItem(cartItemId);
    }

   /* @DeleteMapping("/{id}")

    public ResponseEntity<String> removeItemFromCart(@PathVariable Integer id) throws Exception {
        cartService.removeItemFromCart(id);
        return ResponseEntity.ok("delete success");

    }*/
    @PostMapping("/checkout")
    public String createOrder() throws Exception {
        Long userId = UserContext.getUserId();
        cartService.createOrder(userId);
        return "success";


    }


}
