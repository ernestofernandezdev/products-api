package com.ferdev.restful.api.entities;

import com.ferdev.restful.api.enums.ProductFields;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    @NotNull
    @Positive
    private Integer price;
    @Column(name = "amount")
    @NotNull
    @Positive
    private Integer amount;

    public Product(String name, String description, Integer price, Integer amount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }
}
