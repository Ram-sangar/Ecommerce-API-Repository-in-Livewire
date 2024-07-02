package com.SpringBoot.EcommerceSiteProject.Product.Controller;

import com.SpringBoot.EcommerceSiteProject.Product.model.Product;
import com.SpringBoot.EcommerceSiteProject.Product.Repository.CategoryRepository;
import com.SpringBoot.EcommerceSiteProject.Product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    //private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> product = productService.getAllProduct();
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

   @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws Exception {
        try {
            productService.addProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "User does not exist", e);
        }
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestBody Product productDetails) throws Exception {
         productService.updateProduct(id,productDetails);
        return  ResponseEntity.ok("Product Updated Successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("delete success");
    }



}




