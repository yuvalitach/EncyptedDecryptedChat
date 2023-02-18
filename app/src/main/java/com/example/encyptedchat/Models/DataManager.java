package com.example.encyptedchat.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class DataManager {
    private final FirebaseFirestore dbFireStore;

    private ArrayList<Message> messages;
    private static DataManager single_instance = null;

    private DataManager() {
        dbFireStore = FirebaseFirestore.getInstance();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public static DataManager getInstance() {
        return single_instance;
    }

    public static DataManager initHelper() {
        if (single_instance == null) {
            single_instance = new DataManager();
        }
        return single_instance;
    }


    public FirebaseFirestore getDbFireStore() {
        return dbFireStore;
    }


}



