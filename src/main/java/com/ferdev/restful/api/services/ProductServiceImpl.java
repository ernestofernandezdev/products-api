package com.ferdev.restful.api.services;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.enums.ProductFields;
import com.ferdev.restful.api.exceptions.InvalidQueryParamException;
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
    public List<Product> getProducts(String sort, String order) {

        if (ProductFields.validateSortValue(sort) && (order.equals("asc") || order.equals("desc"))) {
            return this.productRepository.findAll(sort, order);
        } else {
            throw new InvalidQueryParamException("Some of the query params are not valid.");
        }

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
