package com.example.ex21051;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

public class NavigationMenu {

    public static NavigationMenu instance;

    public static NavigationMenu getInstance() {
        if (instance == null) {
            instance = new NavigationMenu();
        }
        return instance;
    }

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
