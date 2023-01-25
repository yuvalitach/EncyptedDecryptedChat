package com.example.encyptedchat.Models;

import java.util.UUID;

public class Message {

    private String description;
    private String messageId;

    public Message(String description) {
        this.description = description;
        this.messageId = UUID.randomUUID().toString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
