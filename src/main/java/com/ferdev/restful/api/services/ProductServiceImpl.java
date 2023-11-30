package com.ferdev.restful.api.services;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.exceptions.ProductNotFoundException;
import com.ferdev.restful.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public List<Product> getProductsOrderedByPrice() {
        return this.productRepository.findByOrderByPriceAsc();
    }

    @Override
    public Optional<Product> getProductById(int id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        this.productRepository.deleteById(id);
    }
}
