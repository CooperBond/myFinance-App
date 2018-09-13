package com.developer.kirill.myfinance;

import java.util.Date;
import java.util.UUID;

public class Expense {
    private UUID ID;
    private Date date;
    private int expense;
    private String expenseName;
    private String category;
    private String note;


    public Expense() {
        this(UUID.randomUUID());
    }

    public Expense(UUID id) {
        ID = id;
    }

    public int getExpense() {
        return expense;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpense(int value) {
        expense = value;
    }

    public void setExpenseName(String name) {
        expenseName = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date dateSet) {
        date = dateSet;
    }

    public UUID getId() {
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
        return "IMG_" + getId().toString() + ".jpg";
    }
}
