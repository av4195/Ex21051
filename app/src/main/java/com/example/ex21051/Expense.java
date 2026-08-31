package com.example.ex21051;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents an expense.
 * It contains information about the expense.
 */
public class Expense {
    public String KEY_ID;
    public String description = "description";
    public String amount = "amount";
    public String category = "category";
    public String date = "date";
    public Object timestamp;

    public Expense() {}

    /**
     * Creates an Expense object with its details.
     *
     * @param keyid the id of the expense
     * @param description the description of the expense
     * @param amount the amount of the expense
     * @param category the category of the expense
     * @param date the date of the expense
     */
    public Expense(String keyid, String description, String amount, String category, String date) {
        this.KEY_ID = keyid;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    /**
     * Creates an Expense object with its details and timestamp.
     *
     * @param keyid the id of the expense
     * @param description the description of the expense
     * @param amount the amount of the expense
     * @param category the category of the expense
     * @param date the date of the expense
     * @param timestamp the timestamp of the expense
     */
    public Expense(String keyid, String description, String amount, String category, String date, Object timestamp) {
        this.KEY_ID = keyid;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.timestamp = timestamp;
        setTimestamp(date);
    }

    /**
     * Returns the description of the expense.
     *
     * @return the description of the expense
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return the amount of the expense
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Returns the category of the expense.
     *
     * @return the category of the expense
     */
    public String getCategory() {
        return category;
    }

    /**
     * Returns the date of the expense.
     *
     * @return the date of the expense
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the id of the expense.
     *
     * @return the id of the expense
     */
    public String getId() {
        return KEY_ID;
    }



    /**
     * Sets the description of the expense.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the amount of the expense.
     *
     * @param amount the new amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Sets the category of the expense.
     *
     * @param category the new category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets the date of the expense.
     *
     * @param date the new date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the timestamp of the expense.
     *
     * @return the timestamp of the expense
     */
    public Object getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the expense.
     * If the timestamp is a number, it also updates the date.
     *
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
        if (timestamp instanceof Long) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            this.date = sdf.format(new Date((Long) timestamp));
        }
    }

    /**
     * Sets the id of the expense.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.KEY_ID = id;
    }
}
