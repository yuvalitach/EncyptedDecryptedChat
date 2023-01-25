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
    private MaterialButton main_BTN_sign_in;
    private Intent intent;
    private final DataManager dataManager = DataManager.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        main_BTN_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int exist;
                String name = main_EDT_name.getText().toString();
                exist = dataManager.findUserInDB(name);
                if (exist == 0) {
                    if (dataManager.getCurrentUser().getRole() == 0){
                        intent = new Intent(SignInActivity.this, EncryptActivity.class);
                    } else {
                        dataManager.loadMessagesFromDB();
                        intent = new Intent(SignInActivity.this, DecryptActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }
                if (exist == 1) {
                    Toast.makeText(SignInActivity.this, "The user is not registered in the system", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void findsViews(){
        main_BTN_sign_in = findViewById(R.id.main_BTN_sign_in);
        main_EDT_name = findViewById(R.id.main_EDT_name);
    }
}
