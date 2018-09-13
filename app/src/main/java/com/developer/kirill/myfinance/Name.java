package com.developer.kirill.myfinance;

import java.util.UUID;

public class Name {
    private String description;
    private UUID nameId;

    public Name() {
        this(UUID.randomUUID());
    }

    public Name(UUID id) {
        nameId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String text) {
        description = text;
    }

    public UUID getNameId() {
        return nameId;
    }
}
