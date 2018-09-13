package com.developer.kirill.myfinance;

import java.util.Date;
import java.util.UUID;

public class Income {
    private UUID ID;
    private Date date;
    private int income;
    private String incomeName;
    private String category;
    private String note;

    public Income() {
        this(UUID.randomUUID());
    }

    public Income(UUID id) {
        ID = id;
    }

    public int getIncome() {
        return income;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncome(int value) {
        income = value;
    }

    public void setIncomeName(String name) {
        incomeName = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date dateSet) {
        date = dateSet;
    }

    public UUID getID() {
        return ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String value) {
        category = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String text) {
        note = text;
    }

    public String getPhotoFilename() {
        return "IMG_" + getID().toString() + ".jpg";
    }
}
