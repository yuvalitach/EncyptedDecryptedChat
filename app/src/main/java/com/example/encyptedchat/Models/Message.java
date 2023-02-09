package com.example.encyptedchat.Models;

import java.util.UUID;

public class Message {

    private String description;
    private String messageId;
    private String senderName;

    public Message(String description, String senderName) {
        this.description = description;
        this.messageId = UUID.randomUUID().toString();
        this.senderName = senderName;
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

    public String getSenderName() {
        return senderName;
    }

    public Message setSenderName(String senderName) {
        this.senderName = senderName;
        return this;
    }
}
