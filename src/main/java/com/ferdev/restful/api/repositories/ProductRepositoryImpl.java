package com.ferdev.restful.api.repositories;

import com.ferdev.restful.api.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    EntityManager entityManager;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Product> findAll(String sort, String order) {
        String strQuery = "SELECT p FROM Product p ORDER BY p." + sort + " " + order;

        TypedQuery<Product> query = this.entityManager.createQuery(strQuery, Product.class);

        return query.getResultList();
    }

    @Override
    public Optional<Product> findById(int id) {
        Product product = this.entityManager.find(Product.class, id);

        if (product != null) {
            return Optional.of(product);
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return this.entityManager.merge(product);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Product product = this.entityManager.find(Product.class, id);

        this.entityManager.remove(product);
    }

}
