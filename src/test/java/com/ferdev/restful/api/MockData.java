package com.ferdev.restful.api;

import com.ferdev.restful.api.entities.Product;
import com.ferdev.restful.api.enums.ProductFields;

import java.util.*;

public class MockData {
    public static List<Product> mockProductList() {
        return new ArrayList<>(Arrays.asList(
                new Product("Heladera", "frio", 100000, 1),
                new Product("Calefactor", "calor", 150000, 2),
                new Product("Teléfono", "celular", 50000, 1),
                new Product("Silla", "hogar", 4000, 4)
        ));
    }

    public static Product mockSingleProduct() {
        return new Product("Heladera", "frio", 100000, 1);
    }

    public static Map<ProductFields, String> mockFilters() {
        HashMap<ProductFields, String> productFieldsStringMap = new HashMap<>();
        productFieldsStringMap.put(ProductFields.NAME, "");
        productFieldsStringMap.put(ProductFields.PRICE, "");
        productFieldsStringMap.put(ProductFields.AMOUNT, "");
        return productFieldsStringMap;
    }
}
