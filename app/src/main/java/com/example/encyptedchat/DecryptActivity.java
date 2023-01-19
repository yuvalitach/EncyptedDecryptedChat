package com.example.encyptedchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DecryptActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> encryptedValues;
    private List<String> decryptedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);

        encryptedValues = new ArrayList<>();
        encryptedValues.add(DecryptChat.encrypt("yuval"));
        // Add your encrypted values to the list
        decryptedValues = DecryptChat.decrypt(encryptedValues);
        Log.d("decryptedValuesTAG", "onCreate: "+decryptedValues);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, decryptedValues);
        listView.setAdapter(adapter);
    }


}