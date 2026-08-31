package com.example.ex21051;

import java.util.ArrayList;


/**
 * This class manages a list of expenses.
 * It uses one shared instance of the list.
 */
public class ExpansesList {
    private static ExpansesList instance;
    private ArrayList<Expense> expanses;

    /**
     * Creates a new empty list of expenses.
     */
    private ExpansesList() {
        expanses = new ArrayList<>();
    }

    /**
     * Returns the shared instance of ExpansesList.
     * Creates a new instance if one does not exist.
     *
     * @return the instance of ExpansesList
     */
    public static ExpansesList getInstance() {
        if (instance == null) {
            instance = new ExpansesList();
        }
        return instance;
    }

    /**
     * Returns the list of expenses.
     *
     * @return the list of expenses
     */
    public ArrayList<Expense> getExpanses() {
        return expanses;
    }

    /**
     * Finds an expense by its id.
     *
     * @param id the id of the expense
     * @return the expense with the matching id, or null if it was not found
     */
    public Expense getExpenseById(String id) {
        return expanses.stream().filter(expense -> expense.getId().equals(id)).findFirst().orElse(null);
    }
}
