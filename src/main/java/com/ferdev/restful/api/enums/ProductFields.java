package com.ferdev.restful.api.enums;

import lombok.Getter;

@Getter
public enum ProductFields {
    ID("id"),
    NAME("name"),
    DESCRIPTION("description"),
    PRICE("price"),
    AMOUNT("amount");

    private final String label;

    ProductFields(String label) {
        this.label = label;
    }

    public static boolean validateSortValue(String sort) {
        for (ProductFields p: values()) {
            if (p.label.equals(sort)) {
                return true;
            }
        }

        return false;
    }
}
