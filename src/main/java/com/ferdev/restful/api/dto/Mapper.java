package com.ferdev.restful.api.dto;

import com.ferdev.restful.api.entities.Product;
import org.springframework.stereotype.Component;

public class Mapper {
    public static ProductDto toDto(Product product) {
        return ProductDto.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .amount(product.getAmount())
                .build();
    }

    public static Product toEntity(ProductDto product) {
        return Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .amount(product.getAmount())
                .build();
    }
}
