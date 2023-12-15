package com.ferdev.restful.api.controllers;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.exceptions.ProductNotFoundException;
import com.ferdev.restful.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(@RequestParam(name = "sort", defaultValue  = "noSort") String sort,
                                                     @RequestParam(name = "order", defaultValue  = "noOrder") String order) {
        List<Product> products = this.productService.getProducts(sort, order);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        Product product = this.productService.getProductById(productId);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = this.productService.createProduct(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable int productId) {
        Product updatedProduct = this.productService.updateProduct(product, productId);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int productId) {
        Product deletedProduct = this.productService.deleteProduct(productId);

        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }
}
