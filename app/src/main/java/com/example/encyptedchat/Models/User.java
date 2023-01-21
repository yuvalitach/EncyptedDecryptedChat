package com.example.encyptedchat.Models;

import java.util.ArrayList;

public class User {
    String name;
    String key;
    ArrayList <String> messageArray;

    public User(String name, String key, ArrayList<String> messageArray) {
        this.name = name;
        this.key = key;
        this.messageArray = messageArray;
    }


    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getKey() {
        return key;
    }

    public User setKey(String key) {
        this.key = key;
        return this;
    }

    public ArrayList<String> getMessageArray() {
        return messageArray;
    }

    public User setMessageArray(ArrayList<String> messageArray) {
        this.messageArray = messageArray;
        return this;
    }
}
