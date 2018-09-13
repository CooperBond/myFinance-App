package com.developer.kirill.myfinance;

import java.util.UUID;

public class Category {
    private String name;
    private UUID categoryId;

    public Category() {
        categoryId = UUID.randomUUID();
    }

    public Category(UUID id) {
        categoryId = id;
    }

    public String getCategory() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public UUID getCategoryId() {
        return categoryId;
    }
}
