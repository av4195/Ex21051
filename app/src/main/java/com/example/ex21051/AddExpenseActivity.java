package com.example.ex21051;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Adi Waizman
 * @version 1.0
 * @since 01/08/2026
 *
 *  This class allows the user to add a new expense or edit an existing expense.
 */

public class AddExpenseActivity extends AppCompatActivity {

    EditText etAmount;
    EditText etDescription;
    EditText etCategory;
    EditText etDate;
    Button btnAddExpanse;
    private FirebaseHelper firebaseHelper;
    private String expenseId = null;

    private boolean isEditing = false;

    /**
     * Creates and initializes the activity.
     * It sets up the views and checks if an expense is being edited.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.editTextDescription);
        etCategory = findViewById(R.id.editTextCatagory);
        etDate = findViewById(R.id.editTextDate);
        btnAddExpanse = findViewById(R.id.button);
        firebaseHelper = new FirebaseHelper();

        // Check for Intent extras
        if (getIntent().hasExtra("id")) {
            isEditing = true;
            expenseId = getIntent().getStringExtra("id");
            if (expenseId != null)
                loadExpenseData(expenseId);
        }

        etDate.setOnClickListener(v -> showDatePicker());

        btnAddExpanse.setOnClickListener(v -> addExpanse());
    }

    /**
     * Opens a date picker and allows the user to select a date.
     */
    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etDate.setText(format.format(calendar.getTime()));
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    /**
     * Creates the options menu for the activity.
     *
     * @param menu the menu to create
     * @return true if the menu was created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles clicks on items in the options menu.
     *
     * @param item the selected menu item
     * @return true if the item was handled
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.OnMenuItemClick(item, this);
//        if (item.getItemId() == R.id.activity_home) {
//            startActivity(new Intent(this, ActivityHome.class));
//            return true;
//        }
//        if (item.getItemId() == R.id.activity_search) {
//            startActivity(new Intent(this, ActivitySearch.class));
//            return true;
//        }
//        if (item.getItemId() == R.id.activity_credits) {
//            startActivity(new Intent(this, ActivityCredits.class));
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Loads the data of an existing expense into the input fields.
     *
     * @param id the id of the expense
     */
    private void loadExpenseData(String id) {
        Expense expense = ExpansesList.getInstance().getExpenseById(id);
        if (expense != null) {
            etAmount.setText(expense.getAmount());
            etDescription.setText(expense.getDescription());
            etCategory.setText(expense.getCategory());
            etDate.setText(expense.getDate());
            btnAddExpanse.setText("Update Expense");
        }
    }

    /**
     * Adds a new expense or updates an existing expense.
     */
    private void addExpanse() {
        String amount = etAmount.getText().toString();
        String description = etDescription.getText().toString();
        String category = etCategory.getText().toString();
        String date = etDate.getText().toString();

        if (isEditing) {
            // Update existing expense
            Expense updatedExpense = new Expense(expenseId, description, amount, category, date);
            firebaseHelper.updateExpense(updatedExpense);
//            dbHelper.updateExpanse(updatedExpense);
            finish();
        } else {
            // Create a new Expanse object
            Expense newExpense = new Expense(null, description, amount, category, date);
            // Insert the new Expanse into the database
            firebaseHelper.addExpense(newExpense);
            finish();
        }
    }
}