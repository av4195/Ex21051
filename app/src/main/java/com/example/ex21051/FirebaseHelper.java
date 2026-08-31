package com.example.ex21051;

import android.media.tv.StreamEventRequest;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class handles all the communication with Firebase.
 * It is used to add, delete, update and get expenses.
 */
public class FirebaseHelper {

    /**
     * An interface used to get the expenses after the data is loaded from Firebase.
     */
    public interface OnDataLoadedListener {
        void onDataLoaded(List<Expense> expenses);
        void onDataCancel();
    }

    private FirebaseDatabase database;

    /**
     * Creates a FirebaseHelper object and connects to Firebase.
     */
    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
    }

    /**
     * Adds a new expense to Firebase.
     *
     * @param expenseToAdd the expense to add
     */
    public void addExpense(Expense expenseToAdd) {
        String keyId = FBref.refExpenses.push().getKey();
        expenseToAdd.setId(keyId);
        FBref.refExpenses.child(keyId).setValue(expenseToAdd);
    }

    /**
     * Deletes an expense from Firebase.
     *
     * @param expense the expense to delete
     */
    public void deleteExpense(Expense expense) {
        FBref.refExpenses.child(expense.getId()).removeValue();
    }

    /**
     * Gets all the expenses from Firebase ordered by their date.
     *
     * @param listener the listener that receives the loaded expenses
     */
    public void getExpensesOrderByDate(OnDataLoadedListener listener){
        List<Expense> expenses = new ArrayList<>();
            Query query = FBref.refExpenses.orderByChild("timestamp");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data :
                            snapshot.getChildren()) {
                        Expense expense = data.getValue(Expense.class);
                        expenses.add(expense);
                    }
                    ExpansesList.getInstance().getExpanses().clear();
                    ExpansesList.getInstance().getExpanses().addAll(expenses);
                    listener.onDataLoaded(expenses);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    /**
     * Updates an existing expense in Firebase.
     *
     * @param expense the expense to update
     */
    public void updateExpense(Expense expense) {
        FBref.refExpenses.child(expense.getId()).setValue(expense);
    }

    /**
     * Filters expenses by description and maximum amount.
     *
     * @param description the description to search for
     * @param maxAmount the maximum amount of the expenses
     * @param listener the listener that receives the filtered expenses
     */
    public void filterExpenses(String description, String maxAmount, OnDataLoadedListener listener) {
        Query query = FBref.refExpenses;

        if (description != null && !description.isEmpty()) {
            // Case: Description is provided (handles cases: both provided OR only description provided)
            query = query.orderByChild("description").equalTo(description);
        } else if (maxAmount != null && !maxAmount.isEmpty()) {
            // Case: Only amount is provided
            try {
                query = query.orderByChild("amount").endAt(maxAmount);
            } catch (Exception e) {
                // Fallback or handle error
            }
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Expense> expenses = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Expense expense = data.getValue(Expense.class);
                    if (expense != null) {
                        expenses.add(expense);
                    }
                }

                // If BOTH description and amount were provided, we filter client-side for amount
                if (description != null && !description.isEmpty() && maxAmount != null && !maxAmount.isEmpty()) {
                    try {
                        int maxLimit = Integer.parseInt(maxAmount);
                        List<Expense> filtered = new ArrayList<>();
                        for (Expense e : expenses) {
                            try {
                                if (Integer.parseInt(e.getAmount()) <= maxLimit) {
                                    filtered.add(e);
                                }
                            } catch (NumberFormatException nfe) {
                                // Skip if amount is not a valid number
                            }
                        }
                        expenses = filtered;
                    } catch (NumberFormatException e) {
                        // Handle invalid maxAmount
                    }
                }
                listener.onDataLoaded(expenses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onDataCancel();
            }
        });
    }

    /**
     * Gets all the expenses from Firebase.
     *
     * @param listener the listener that receives the loaded expenses
     * @return null because the data is loaded asynchronously
     */
    public List<Expense> getExpenses(OnDataLoadedListener listener) {
        List<Expense> expenses = new ArrayList<>();
        FBref.refExpenses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data :
                        snapshot.getChildren()) {
                    Expense expense = data.getValue(Expense.class);
                    expenses.add(expense);
                }
                ExpansesList.getInstance().getExpanses().clear();
                ExpansesList.getInstance().getExpanses().addAll(expenses);
                listener.onDataLoaded(expenses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onDataCancel();
            }
        });
        return null;
    }
}
