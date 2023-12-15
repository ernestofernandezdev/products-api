package com.ferdev.restful.api.services;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.exceptions.ProductNotFoundException;
import com.ferdev.restful.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Product> getProducts(String order) {
        if (order != null && order.equals("price")) {
            return this.productRepository.findByOrderByPriceAsc();
        }

        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> optProduct = this.productRepository.findById(id);
        if (optProduct.isPresent()) {
            return optProduct.get();
        } else {
            throw new ProductNotFoundException("There is no product with id " + id);
        }
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(0);
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, int id) {
        if (this.productRepository.findById(id).isPresent()) {
            product.setId(id);
            this.productRepository.save(product);
            return product;
        } else {
            throw new ProductNotFoundException("There is no product with id " + id);
        }
    }

    @Override
    public Product deleteProduct(int id) {
        Optional<Product> optProduct = this.productRepository.findById(id);

        if (optProduct.isPresent()) {
            this.productRepository.deleteById(id);
            return optProduct.get();
        } else {
            throw new ProductNotFoundException("There is no product with id " + id);
        }
    }
}
