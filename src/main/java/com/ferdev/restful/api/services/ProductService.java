package com.ferdev.restful.api.services;

import com.ferdev.restful.api.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts();

    List<Product> getProductsOrderedByPrice();

    Optional<Product> getProductById(int id);

    Product createProduct(Product product);

    void updateProduct(Product product);

    void deleteProduct(int id);
}
