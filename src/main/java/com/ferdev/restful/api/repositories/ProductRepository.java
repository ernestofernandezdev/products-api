package com.ferdev.restful.api.repositories;

import com.ferdev.restful.api.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByOrderByPriceAsc();
}
