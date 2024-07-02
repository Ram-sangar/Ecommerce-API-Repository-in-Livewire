package com.SpringBoot.EcommerceSiteProject.Cart.Service;

import com.SpringBoot.EcommerceSiteProject.Cart.Repository.CartItemRepository;
import com.SpringBoot.EcommerceSiteProject.Cart.model.Cart;
import com.SpringBoot.EcommerceSiteProject.Cart.model.CartItem;
import com.SpringBoot.EcommerceSiteProject.Product.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class CartServiceTest {

    @Autowired
    CartService cartService;

    @MockBean
    CartItemRepository cartItemRepository;


    @TestConfiguration
    static class CartTestConfig {
        @Bean
        public CartService cartService() {
            return new CartService();
        }

    }

    @Test
    void calculateTotalForSingleItem() {

        Product product = Product.builder().id(1).product_Name("laptop")
                .price(50000).gstPercentage(18).build();

        CartItem cartItem = CartItem.builder().quantity(1).build();

        List<CartItem> cartItemList = List.of(cartItem);

        Cart cart = Cart.builder().id(1).build();

        when(cartItemRepository.save(cartItem)).then(returnsFirstArg());
        when(cartItemRepository.findByCartId(any())).thenReturn(cartItemList);

        Cart updatedCart = cartService.calculateTotal(cartItem,cart,product);

        Assertions.assertEquals(updatedCart.getTotalPrice(), 50000);
        Assertions.assertEquals(updatedCart.getGstAmount(), 9000);
        Assertions.assertEquals(updatedCart.getTotalAmountWithGST(), 59000);
    }
}