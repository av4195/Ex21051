package com.example.ex21051;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ActivityHome extends AppCompatActivity {

    private RecyclerView rvExpanses;
    ExpenseAdapter adapter;
    private List<Expense> expenseList;
    AlertDialog.Builder adb;
    AlertDialog.Builder adb2;
    LinearLayout myDialog;
    EditText etd, eta, etc, etDate;
    TextView tvOverallAmount;
    private ExpansesList expansesList;
//    private HelperDB dbHelper;
    FirebaseHelper firebaseHelper = new FirebaseHelper();
    FloatingActionButton btnAddExpense;


    /**
     *this method is for
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvExpanses = findViewById(R.id.rvExpanses);
        rvExpanses.setLayoutManager(new LinearLayoutManager(this));
        tvOverallAmount = findViewById(R.id.tvOverallAmount);
        expansesList = ExpansesList.getInstance();
        expenseList = expansesList.getExpanses();
        btnAddExpense = findViewById(R.id.btn_add_expense);
        btnAddExpense.setOnClickListener(v -> {
            startActivity(new Intent(ActivityHome.this, AddExpenseActivity.class));
        });
//        dbHelper = new HelperDB(this);
//        if (expenseList.isEmpty()) {
//            // Add some dummy data using Expense class if list is empty
//            expenseList.add(new Expense(1L, "Grocery shopping", "50.00", "Food", "2026-08-10"));
//            expenseList.add(new Expense(2L, "Gas station", "40.00", "Transport", "2026-08-09"));
//            expenseList.add(new Expense(3L, "Netflix subscription", "15.00", "Entertainment", "2026-08-01"));
//        }
        getExpensesByDate();
        adapter = new ExpenseAdapter(expenseList);
        adapter.setOnItemClickListener(expense -> {
            setupItemAlertDialog(expense);
        });
        rvExpanses.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.OnMenuItemClick(item, this);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void getExpenses()
    {
        firebaseHelper.getExpenses(new FirebaseHelper.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<Expense> expenses) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onDataCancel() {
                Toast.makeText(ActivityHome.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getExpensesByDate()
    {
        firebaseHelper.getExpensesOrderByDate(new FirebaseHelper.OnDataLoadedListener() {
            @Override
            public void onDataLoaded(List<Expense> expenses) {
                calculateOverallExpense(expenses);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onDataCancel() {
                Toast.makeText(ActivityHome.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void calculateOverallExpense(List<Expense> expenses) {
        int sum = 0;
        for (Expense temp:
             expenses) {
            sum += Integer.parseInt(temp.getAmount());
        }
        tvOverallAmount.setText(sum + "$");
    }

    private void setupItemAlertDialog(Expense expense)
    {
        adb = new AlertDialog.Builder(this);
        adb.setTitle("What do you want to do?");

        adb.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ActivityHome.this, AddExpenseActivity.class);
                intent.putExtra("id", expense.getId());
                startActivity(intent);
            }
        });

        adb.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int position = expenseList.indexOf(expense);
                expenseList.remove(expense);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, expenseList.size());
//                dbHelper.deleteExpanse(expense.getId());
                firebaseHelper.deleteExpense(expense);
                Toast.makeText(ActivityHome.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog ad1 = adb.create();
        ad1.show();
    }
}