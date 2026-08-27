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

public class FirebaseHelper {

    public interface OnDataLoadedListener {
        void onDataLoaded(List<Expense> expenses);
        void onDataCancel();
    }

    public interface Whatever{
        void whatever();
    }
    private FirebaseDatabase database;

    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
    }

    public void addExpense(Expense expenseToAdd) {
        String keyId = FBref.refExpenses.push().getKey();
        expenseToAdd.setId(keyId);
        FBref.refExpenses.child(keyId).setValue(expenseToAdd);
    }

    public void deleteExpense(Expense expense) {
        FBref.refExpenses.child(expense.getId()).removeValue();
    }

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

    public void updateExpense(Expense expense) {
        FBref.refExpenses.child(expense.getId()).setValue(expense);
    }

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
