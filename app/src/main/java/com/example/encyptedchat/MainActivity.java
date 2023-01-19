package com.example.encyptedchat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class MainActivity extends AppCompatActivity {

    private static final String ALGORITHM = "AES";
    private static final String KEY = "1Hbfh667adfDEJ78"; // 128 bit key

    private EditText messageInput;
    private ListView chatList;
    private Button sendButton;

    private List<String> messages = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageInput = findViewById(R.id.message_input);
        chatList = findViewById(R.id.chat_list);
        sendButton = findViewById(R.id.send_button);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messages);
        chatList.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageInput.getText().toString();
                try {
                    message = encrypt(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                messages.add(message);
                adapter.notifyDataSetChanged();
                messageInput.setText("");
                // send message to server
            }
        });

        // handle receiving messages from server
        String receivedMessage = "";
        try {
            receivedMessage = decrypt(receivedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        messages.add(receivedMessage);
        adapter.notifyDataSetChanged();
    }

    public static String encrypt(String value) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes("UTF-8"), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes("UTF-8"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }
        return  null;
    }


    public static String decrypt(String value) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes("UTF-8"), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedValue64 = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            decryptedValue64 = Base64.getDecoder().decode(value);
        }
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
        return new String(decryptedByteValue, "UTF-8");
    }
}

