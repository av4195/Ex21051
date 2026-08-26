package com.example.ex21051;

import static com.example.ex21051.Expanses.DESCRIPTION;
import static com.example.ex21051.Expanses.KEY_ID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HelperDB extends SQLiteOpenHelper {

    // --- Constants ---
    public static final String DATABASE_NAME = "dbexam.db";
    public static final int DATABASE_VERSION = 1;

    // --- Constructor ---
    public HelperDB(Context context) {
        // Pass the context, database name, a null cursor factory, and the version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // --- Required Methods ---

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + Expanses.TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DESCRIPTION + " TEXT, " +
                Expanses.AMOUNT + " REAL, " +
                Expanses.DATE + " TEXT, " +
                Expanses.CATEGORY + " TEXT)";

        Log.d(HelperDB.class.getName(), "Executing: " + createTableQuery);
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Expanses.TABLE_NAME);
        onCreate(db);
    }

    public long addExpanse(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, expense.getDescription());
        values.put(Expanses.AMOUNT, expense.getAmount());
        values.put(Expanses.CATEGORY, expense.getCategory());
        values.put(Expanses.DATE, expense.getDate());

        long id = db.insert(Expanses.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void deleteExpanse(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Expanses.TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public int updateExpanse(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, expense.getDescription());
        values.put(Expanses.AMOUNT, expense.getAmount());
        values.put(Expanses.CATEGORY, expense.getCategory());
        values.put(Expanses.DATE, expense.getDate());

        int result = db.update(Expanses.TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
        db.close();
        return result;
    }



    public List<Expense> getAllExpenses() {
        return filterExpenses(null, null);
    }

    public List<Expense> filterExpenses(String description, String maxAmount) {
        List<Expense> expenseList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM " + Expanses.TABLE_NAME + " WHERE 1=1");
        List<String> args = new ArrayList<>();

        if (description != null && !description.isEmpty()) {
            query.append(" AND ").append(DESCRIPTION).append(" LIKE ?");
            args.add("%" + description + "%");
        }

        if (maxAmount != null && !maxAmount.isEmpty()) {
            query.append(" AND ").append(Expanses.AMOUNT).append(" <= ?");
            args.add(maxAmount);
        }

        query.append(" ORDER BY ").append(Expanses.DATE).append(" DESC");

        Log.d(HelperDB.class.getName(), "Executing: " + query + " with args: " + args);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query.toString(), args.toArray(new String[0]));

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense(
                        cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Expanses.AMOUNT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Expanses.CATEGORY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Expanses.DATE))
                );
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return expenseList;
    }

}
