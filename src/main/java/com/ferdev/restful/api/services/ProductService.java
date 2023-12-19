package com.ferdev.restful.api.services;

import com.ferdev.restful.api.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts(String sort, String order);

    Product getProductById(int id);

    Product createProduct(Product product);

    Product updateProduct(Product product, int id);

    Product deleteProduct(int id);

    void validateProduct(Product product);
}
