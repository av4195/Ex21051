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

    public Expense getExpenseById(long id) {
        return expanses.stream().filter(expense -> expense.getId() == id).findFirst().orElse(null);
    }
}
