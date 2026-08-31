package com.example.ex21051;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

/**
 * This class handles the navigation menu.
 * It opens the correct activity according to the item that was clicked.
 */

public class NavigationMenu {

    public static NavigationMenu instance;

    /**
     * Returns the instance of NavigationMenu.
     * If there is no instance yet, it creates a new one.
     *
     * @return the NavigationMenu instance
     */
    public static NavigationMenu getInstance() {
        if (instance == null) {
            instance = new NavigationMenu();
        }
        return instance;
    }

    /**
     * Handles a click on an item in the navigation menu.
     * Opens the correct activity according to the selected item.
     *
     * @param item the menu item that was clicked
     * @param context the context used to open the activity
     * @return true if the item was found and handled, otherwise false
     */
    public boolean OnMenuItemClick(MenuItem item, Context context)
    {
        if (item.getItemId() == R.id.activity_home) {
            context.startActivity(new Intent(context, ActivityHome.class));
            return true;
        }
        if (item.getItemId() == R.id.activity_search) {
            context.startActivity(new Intent(context, ActivitySearch.class));
            return true;
        }
        if(item.getItemId() == R.id.activity_add) {
            context.startActivity(new Intent(context, AddExpenseActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.activity_credits) {
            context.startActivity(new Intent(context, ActivityCredits.class));
            return true;
        }
        return false;
    }
}
