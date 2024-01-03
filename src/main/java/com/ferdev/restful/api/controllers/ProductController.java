package com.ferdev.restful.api.controllers;

import com.ferdev.restful.api.dto.Mapper;
import com.ferdev.restful.api.dto.ProductDto;
import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.enums.ProductFields;
import com.ferdev.restful.api.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getProducts(@RequestParam(name = "sort", defaultValue  = "id") String sort,
                                                        @RequestParam(name = "order", defaultValue  = "asc") String order,
                                                        @RequestParam(name = "name", defaultValue  = "") String name,
                                                        @RequestParam(name = "price", defaultValue  = "") String price,
                                                        @RequestParam(name = "amount", defaultValue  = "") String amount) {

        HashMap<ProductFields, String> filters = new HashMap<>();
        filters.put(ProductFields.NAME, name);
        filters.put(ProductFields.PRICE, price);
        filters.put(ProductFields.AMOUNT, amount);

        List<Product> products = this.productService.getProducts(sort, order, filters);

        List<ProductDto> dtoProducts = products.stream()
                .map(Mapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoProducts);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int productId) {
        Product product = this.productService.getProductById(productId);

        return ResponseEntity.ok(Mapper.toDto(product));
    }

    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto product) {
        Product savedProduct = this.productService.createProduct(Mapper.toEntity(product));

        return new ResponseEntity<>(Mapper.toDto(savedProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto product, @PathVariable int productId) {
        Product updatedProduct = this.productService.updateProduct(Mapper.toEntity(product), productId);

        return ResponseEntity.ok(Mapper.toDto(updatedProduct));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable int productId) {
        Product deletedProduct = this.productService.deleteProduct(productId);

        return ResponseEntity.ok(Mapper.toDto(deletedProduct));
    }
}
