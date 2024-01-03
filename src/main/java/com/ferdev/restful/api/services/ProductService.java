package com.ferdev.restful.api.services;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.enums.ProductFields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    List<Product> getProducts(String sort, String order, Map<ProductFields, String> filters);

    Product getProductById(int id);

    Product createProduct(Product product);

    Product updateProduct(Product product, int id);

    Product deleteProduct(int id);
}
