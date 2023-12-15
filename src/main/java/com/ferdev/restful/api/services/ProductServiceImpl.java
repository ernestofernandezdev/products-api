package com.ferdev.restful.api.services;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.exceptions.InvalidProductException;
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
    public List<Product> getProducts(String sort, String order) {

        return switch (sort){
            case "price" -> order.equals("desc") ?
                    this.productRepository.findByOrderByPriceDesc() :
                    this.productRepository.findByOrderByPriceAsc();
            case "name" -> order.equals("desc") ?
                    this.productRepository.findByOrderByNameDesc() :
                    this.productRepository.findByOrderByNameAsc();
            case "amount" -> order.equals("desc") ?
                    this.productRepository.findByOrderByAmountDesc() :
                    this.productRepository.findByOrderByAmountAsc();
            default -> this.productRepository.findAll();
        };

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
        this.validateProduct(product);

        product.setId(0);
        return this.productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, int id) {
        this.validateProduct(product);

        if (this.productRepository.findById(id).isPresent()) {
            product.setId(id);
            this.productRepository.save(product);

            return product;
        } else {
            throw new ProductNotFoundException("There is no product with id " + id);
        }
    }

    private void validateProduct(Product product) {
        String name = product.getName();
        Integer price = product.getPrice();
        Integer amount = product.getAmount();

        if (name == null || name.isEmpty()) {
            throw new InvalidProductException("Must provide a valid name");
        }

        if (price == null || price <= 0) {
            throw new InvalidProductException("Price must be positive");
        }

        if (amount == null || amount <= 0) {
            throw new InvalidProductException("Amount must be positive");
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
