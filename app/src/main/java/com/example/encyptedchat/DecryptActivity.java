package com.example.encyptedchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.encyptedchat.Models.DataManager;
import com.example.encyptedchat.Models.DecryptEncrypt;
import com.example.encyptedchat.Models.Message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DecryptActivity extends AppCompatActivity {

    private ArrayList<Message> encryptedValues;
    private PopupWindow popupWindow;
    private final DataManager dataManager = DataManager.getInstance();
    private RecyclerView myMessages;
    private Button send_BTN;
    private EditText inputText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);
        findViews();
        encryptedValues = new ArrayList<>();
        loadMessagesFromDB();

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String key = bundle.getString("key");


        send_BTN.setOnClickListener(view -> {
            try {
                String encryptMessage = DecryptEncrypt.encrypt(inputText.getText().toString(), key);
                Message temp = new Message(encryptMessage, name , encryptedValues.size());
                int length = encryptedValues.size();
                encryptedValues.add(length,temp);
                storeMessagesInDB(temp);
                initAdapter();
                inputText.setText("");
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });



    }

    private void findViews() {
        myMessages = findViewById(R.id.myMessages);
        send_BTN = findViewById(R.id.send_BTN);
        inputText = findViewById(R.id.inputText);
    }

    private void initAdapter() {
        Collections.sort(encryptedValues,new orderComparator());
        ArrayList<Message> messages = encryptedValues;
        MessageAdapter messageAdapter = new MessageAdapter(this, messages);
        myMessages.setLayoutManager(new LinearLayoutManager(this));
        myMessages.setHasFixedSize(true);
        myMessages.setAdapter(messageAdapter);

        messageAdapter.setMessageListener(new MessageAdapter.MessageListener() {
            @Override
            public void decryptMessage(Message message, int position) throws Exception {
                onButtonShowPopupWindowClick(findViewById(R.id.activity_decrypt1),message);
            }

        });
    }

    public void onButtonShowPopupWindowClick(View view , Message message) throws Exception {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, 800, 400, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        MaterialButton popup_BTN_save =(MaterialButton) popupView.findViewById(R.id.popup_BTN_save);
        MaterialButton popup_BTN_exit =(MaterialButton) popupView.findViewById(R.id.popup_BTN_exit);
        TextInputEditText popup_LBL_key = (TextInputEditText) popupView.findViewById(R.id.popup_LBL_key);
        TextInputLayout popUp_EDT_key = (TextInputLayout) popupView.findViewById(R.id.popUp_EDT_key);



        popup_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    popup_LBL_key.setText(DecryptEncrypt.decrypt(message.getDescription(), popup_LBL_key.getText().toString()));
                    popUp_EDT_key.setHint("message:");
                } catch (Exception e) {
                    Toast.makeText(DecryptActivity.this, "This key is not correct", Toast.LENGTH_SHORT).show();
                }

            }
        });
        popup_BTN_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void loadMessagesFromDB() {
        CollectionReference myRef = dataManager.getDbFireStore().collection("Messages");
        myRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        encryptedValues.add(document.toObject(Message.class));
                    }
                    initAdapter();
                } else {
                    Log.d("pttt", "Error getting documents: ", task.getException());
                }
            }
        });

    }

    public void storeMessagesInDB(Message message) {
        dataManager.getDbFireStore().collection("Messages").document(message.getMessageId()).set(message);
    }
}

