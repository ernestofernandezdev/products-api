package com.ferdev.restful.api.repositories;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.enums.ProductFields;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Product> findAll(String sort, String order, Map<ProductFields, String> filters) {

        StringBuilder strQuery = new StringBuilder("SELECT p FROM Product p");

        for (ProductFields field: filters.keySet()) {
            if (filters.get(field) != null && !filters.get(field).isEmpty()) {

                if (strQuery.toString().contains("WHERE")) {
                    strQuery.append(" AND");
                } else {
                    strQuery.append(" WHERE");
                }

                if (field.getType() == Integer.class) {
                    strQuery.append(" p." + field.getLabel() + " = :" + field.getLabel());
                } else {
                    strQuery.append(" UPPER(p." + field.getLabel() + ") LIKE UPPER(:" + field.getLabel() + ")");
                }

            }
        }

        strQuery.append(" ORDER BY p." + sort + " " + order);



        TypedQuery<Product> query = this.entityManager.createQuery(strQuery.toString(), Product.class);

        for (ProductFields field: filters.keySet()) {

            if (filters.get(field) != null && !filters.get(field).isEmpty()) {

                if (field.getType() == Integer.class) {
                    query.setParameter(field.getLabel(), filters.get(field));
                } else {
                    query.setParameter(field.getLabel(), "%" + filters.get(field) + "%");
                }

            }
        }



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
