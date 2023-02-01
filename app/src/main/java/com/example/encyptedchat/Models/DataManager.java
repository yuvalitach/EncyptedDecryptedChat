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
    private final FirebaseAuth firebaseAuth;
    private final FirebaseFirestore dbFireStore;
    private final FirebaseStorage storage;


    private User currentUser;
    private ArrayList<String> encryptedValues;
    private static DataManager single_instance = null;

    private DataManager(){
        firebaseAuth = FirebaseAuth.getInstance();
        dbFireStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public ArrayList<String> getEncryptedValues() {
        return encryptedValues;
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

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseFirestore getDbFireStore() {
        return dbFireStore;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public DataManager setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        return this;
    }

    public void storeUserInDB(User userToStore) {
        dbFireStore.collection("Users")
                .document(userToStore.getName())
                .set(userToStore)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("pttt", "DocumentSnapshot Successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("pttt", "Error adding document", e);
                    }
                });
    }

    public int findUserInDB(String name){
        final int[] answer = {0};
        CollectionReference myRef = dbFireStore.collection("Users");
        myRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        User loadedUser = document.toObject(User.class);
                        if (loadedUser.getName().equals(name)) {
                                DataManager.getInstance().setCurrentUser(loadedUser);
                                Log.d("pttt", document.getId() + " => " + document.getData());
                        } else{
                            answer[0] = 1;
                        }
                    }
                } else {
                    Log.d("pttt", "Error getting documents: ", task.getException());
                }
            }
        });
        return answer[0];
    }
    public void loadMessagesFromDB() {
        CollectionReference myRef = dbFireStore.collection("Messages");
        myRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        encryptedValues.add(document.toString());
                    }
                } else {
                    Log.d("pttt", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void storeMessagesInDB(String message) {
        Message temp = new Message(message);
        dbFireStore.collection("Messages").document(temp.getMessageId()).set(temp);
        encryptedValues.add(message);
    }

    }
