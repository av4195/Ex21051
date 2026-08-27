package com.example.ex21051;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Expense {
    public String KEY_ID;
    public String description = "description";
    public String amount = "amount";
    public String category = "category";
    public String date = "date";
    public Object timestamp;

    public Expense() {}

    public Expense(String keyid, String description, String amount, String category, String date) {
        this.KEY_ID = keyid;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public Expense(String keyid, String description, String amount, String category, String date, Object timestamp) {
        this.KEY_ID = keyid;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.timestamp = timestamp;
        setTimestamp(date);
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return KEY_ID;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
        if (timestamp instanceof Long) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            this.date = sdf.format(new Date((Long) timestamp));
        }
    }

    public void setId(String id) {
        this.KEY_ID = id;
    }
}
