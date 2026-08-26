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
    private FirebaseDatabase database;

    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
    }

    public void addExpense(Expense expenseToAdd)
    {
        String keyId = FBref.refExpenses.push().getKey();
        assert keyId == null;
        expenseToAdd.setId(Long.getLong((keyId)));
//        database.getReference("expenses/" + keyId).setValue(expenseToAdd);
        FBref.refExpenses.child(keyId).setValue(expenseToAdd);
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
                    listener.onDataLoaded(expenses);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    public void updateExpense(Expense expense)
    {
        FBref.refExpenses.child(String.valueOf(expense.getId())).setValue(expense);
    }

    public void filterExpenses(String description, String maxAmount, OnDataLoadedListener listener) {
        Query query = FBref.refExpenses;
        if (!description.isEmpty()) {
            query.orderByChild("description").equalTo(description);
        } else if (!maxAmount.isEmpty() && description.isEmpty()) {
            query.orderByChild("amount").endAt(Integer.parseInt(maxAmount));
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Expense> expenses = new ArrayList<>();
                for (DataSnapshot data :
                        snapshot.getChildren()) {
                    Expense expense = data.getValue(Expense.class);
                    expenses.add(expense);
                }
                if (!maxAmount.isEmpty() && !description.isEmpty())
                {
                    for (Expense expense :
                         expenses) {
                        if (Integer.parseInt(expense.amount) <= Integer.parseInt(maxAmount))
                            expenses.remove(expense);
                    }
                }
                listener.onDataLoaded(expenses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Expense> getExpenses(OnDataLoadedListener listener)
    {
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
