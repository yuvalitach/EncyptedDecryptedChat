package com.example.encyptedchat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.encyptedchat.Models.DataManager;
import com.example.encyptedchat.Models.DecryptEncrypt;
import com.example.encyptedchat.Models.Message;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class DecryptActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<Message> encryptedValues;
    private List<String> decryptedValues;
    private PopupWindow popupWindow;
    private final DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        listView = (ListView) findViewById(R.id.list_view);
        //listView.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        encryptedValues = dataManager.getMessages();

        findViewById(R.id.activity_decrypt1).post(new Runnable() {
            public void run() {
                onButtonShowPopupWindowClick(findViewById(R.id.activity_decrypt1));
            }
        });



        encryptedValues = new ArrayList<>();

        try {
//            encryptedValues.add(DecryptEncrypt.encrypt("yuval"));
//            encryptedValues.add(DecryptEncrypt.encrypt("shaked"));
//            encryptedValues.add(DecryptEncrypt.encrypt("Yehonatan"));
//            encryptedValues.add(DecryptEncrypt.encrypt("Adir"));
//            encryptedValues.add(DecryptEncrypt.encrypt("Gaby"));
//            encryptedValues.add(DecryptEncrypt.encrypt("Orna"));


        } catch (Exception e) {
            e.printStackTrace();
        }

//        userToStore=new User("Yuval","abcdefg", encryptedValues,0);
//        dataManager.setCurrentUser(userToStore);
//        dataManager.storeUserInDB(userToStore);



    }

    public void onButtonShowPopupWindowClick(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_up_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        MaterialButton popup_BTN_save =(MaterialButton) popupView.findViewById(R.id.popup_BTN_save);
        TextInputEditText popup_LBL_accountName = (TextInputEditText) popupView.findViewById(R.id.popup_LBL_accountName);;


        popup_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                accountName = popup_LBL_accountName.getText().toString();
                popupWindow.dismiss();
//                saveManuelAccount();
            }
        });
    }

    public void updateAdapter (){
        // Add your encrypted values to the list
        //encryptedValues = DecryptEncrypt.decrypt(encryptedValues);
        //Log.d("decryptedValuesTAG", "onCreate: "+decryptedValues);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, decryptedValues);
        listView.setAdapter(adapter);

    }
}