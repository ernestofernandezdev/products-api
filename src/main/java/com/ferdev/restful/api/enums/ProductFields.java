package com.ferdev.restful.api.enums;

import lombok.Getter;

@Getter
public enum ProductFields {
    ID("id", Integer.class),
    NAME("name", String.class),
    DESCRIPTION("description", String.class),
    PRICE("price", Integer.class),
    AMOUNT("amount", Integer.class);

    private final String label;
    private final Class type;

    ProductFields(String label, Class type) {
        this.label = label;
        this.type = type;
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
