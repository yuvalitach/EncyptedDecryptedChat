package com.example.encyptedchat;

import android.app.Application;

import com.example.encyptedchat.Models.DataManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.initHelper();
    }
}
