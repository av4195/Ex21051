package com.example.ex21051;

import java.util.ArrayList;

public class ExpansesList {
    private static ExpansesList instance;
    private ArrayList<Expense> expanses;

    private ExpansesList() {
        expanses = new ArrayList<>();
    }

    public static ExpansesList getInstance() {
        if (instance == null) {
            instance = new ExpansesList();
        }
        return instance;
    }

    public ArrayList<Expense> getExpanses() {
        return expanses;
    }

    public Expense getExpenseById(String id) {
        return expanses.stream().filter(expense -> expense.getId().equals(id)).findFirst().orElse(null);
    }
}
