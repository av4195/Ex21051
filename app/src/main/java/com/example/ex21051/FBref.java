package com.example.ex21051;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class contains references to the Firebase database.
 * It is used to access the expenses data in Firebase.
 */
public class FBref {
    public static FirebaseDatabase FDBD = FirebaseDatabase.getInstance();
    public static DatabaseReference refExpenses = FDBD.getReference("expenses");// folder.  q -> Child/inside the folder
}
