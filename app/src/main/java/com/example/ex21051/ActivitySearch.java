package com.example.ex21051;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ActivitySearch extends AppCompatActivity {

    private EditText etDescription, etMaxAmount;
    private Button btnSearch;
    private RecyclerView rvResults;
    private ExpenseAdapter adapter;
    private List<Expense> resultsList;
    private HelperDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etDescription = findViewById(R.id.et_description);
        etMaxAmount = findViewById(R.id.etFilterAmount);
        btnSearch = findViewById(R.id.btnSearch);
        rvResults = findViewById(R.id.rvFilterdExpanses);

        dbHelper = new HelperDB(this);
        resultsList = new ArrayList<>();
        adapter = new ExpenseAdapter(resultsList);

        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String desc = etDescription.getText().toString();
            String amount = etMaxAmount.getText().toString();

            List<Expense> filtered = dbHelper.filterExpenses(desc, amount);
            resultsList.clear();
            resultsList.addAll(filtered);
            adapter.notifyDataSetChanged();
        });
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

}