package com.example.ex21051;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * This activity displays the credits of the application.
 */
public class ActivityCredits extends AppCompatActivity {

    /**
     * Creates and initializes the activity.
     *
     * @param savedInstanceState the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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