package com.developer.kirill.myfinance;

import java.util.UUID;

public class StatisticElement {
    private String description;
    private String element;
    private UUID elementId;

    public StatisticElement() {

    }

    public String getDescription() {
        return description;
    }

    public String getElement() {
        return element;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public UUID getElementId() {
        return elementId;
    }

    public void setElementId(UUID Id) {
        elementId = Id;
    }
}
