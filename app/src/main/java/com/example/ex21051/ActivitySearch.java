package com.example.ex21051;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * This activity allows the user to search for expenses.
 * The user can filter expenses by description and amount.
 */
public class ActivitySearch extends AppCompatActivity {

    private EditText etDescription, etMaxAmount;
    private Button btnSearch;
    private RecyclerView rvResults;
    private ExpenseAdapter adapter;
    private List<Expense> resultsList;
    private FirebaseHelper firebaseHelper;


    /**
     * Creates and initializes the activity.
     * It sets up the views, RecyclerView and search button.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etDescription = findViewById(R.id.et_description);
        etMaxAmount = findViewById(R.id.etFilterAmount);
        btnSearch = findViewById(R.id.btnSearch);
        rvResults = findViewById(R.id.rvFilterdExpanses);


        firebaseHelper = new FirebaseHelper();
        resultsList = new ArrayList<>();
        adapter = new ExpenseAdapter(resultsList);

        rvResults.setLayoutManager(new LinearLayoutManager(this));
        rvResults.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> {
            String desc = etDescription.getText().toString();
            String amount = etMaxAmount.getText().toString();

            firebaseHelper.filterExpenses(desc, amount, new FirebaseHelper.OnDataLoadedListener(){
                @Override
                public void onDataLoaded(List<Expense> expenses) {
                    resultsList.clear();
                    resultsList.addAll(expenses);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onDataCancel() {

                }
            });
        });
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
     * @return true after handling the selected item
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        NavigationMenu navigationMenu = NavigationMenu.getInstance();
        navigationMenu.OnMenuItemClick(item, this);
        return true;
    }
}