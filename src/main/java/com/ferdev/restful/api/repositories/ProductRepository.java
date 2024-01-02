package com.ferdev.restful.api.repositories;

import com.ferdev.restful.api.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll(String sort, String order);

    Optional<Product> findById(int id);

    Product save(Product product);

    void deleteById(int id);

}
