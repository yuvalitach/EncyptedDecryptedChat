package com.example.encyptedchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.encyptedchat.Models.DataManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText main_EDT_name;
    private TextInputEditText main_EDT_key;
    private MaterialButton main_BTN_sign_in;
    private Intent intent;
    private final DataManager dataManager = DataManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);
        findsViews();
        main_BTN_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = main_EDT_name.getText().toString();
                String key = main_EDT_key.getText().toString();
                    if(key.length()<16){
                        Toast.makeText(SignInActivity.this, "Please enter a 16 character key", Toast.LENGTH_SHORT).show();
                        main_EDT_key.setText("");
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putString("key", key);
                        intent = new Intent(SignInActivity.this, DecryptActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }
            }
        });
    }


    private void findsViews(){
        main_BTN_sign_in = findViewById(R.id.main_BTN_sign_in);
        main_EDT_name = findViewById(R.id.main_EDT_name);
        main_EDT_key = findViewById(R.id.main_EDT_key);
    }
}
