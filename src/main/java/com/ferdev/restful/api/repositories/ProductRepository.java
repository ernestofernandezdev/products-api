package com.ferdev.restful.api.repositories;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.enums.ProductFields;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductRepository {;

    List<Product> findAll(String sort, String order, Map<ProductFields, String> filters);

    Optional<Product> findById(int id);

    Product save(Product product);

    void deleteById(int id);

}
