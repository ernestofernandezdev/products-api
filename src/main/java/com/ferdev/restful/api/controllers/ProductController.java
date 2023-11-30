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
    public ResponseEntity<List<Product>> getProducts(@RequestParam(name = "order", required = false) String order) {
        List<Product> products;
        if (order != null && order.equals("price")) {
            products = this.productService.getProductsOrderedByPrice();
        } else {
            products = this.productService.getProducts();
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable int productId) {
        if (this.productService.getProductById(productId).isPresent()) {
            return new ResponseEntity<>(this.productService.getProductById(productId).get(), HttpStatus.OK);
        } else {
            throw new ProductNotFoundException("There is no product with id " + productId);
        }
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(0);
        Product savedProduct = this.productService.createProduct(product);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable int productId) {
        if (this.productService.getProductById(productId).isPresent()) {
            product.setId(productId);
            this.productService.updateProduct(product);

            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            throw new ProductNotFoundException("There is no product with id " + productId);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int productId) {
        Optional<Product> optProduct = this.productService.getProductById(productId);
        if (optProduct.isPresent()) {
            this.productService.deleteProduct(productId);

            return new ResponseEntity<>(optProduct.get(), HttpStatus.OK);
        } else {
            throw new ProductNotFoundException("There is no product with id " + productId);
        }
    }
}
