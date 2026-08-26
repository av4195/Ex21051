package com.example.ex21051;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FBref {
    public static FirebaseDatabase FDBD = FirebaseDatabase.getInstance();
    public static DatabaseReference refExpenses = FDBD.getReference("expenses");// folder.  q -> Child/inside the folder
}
