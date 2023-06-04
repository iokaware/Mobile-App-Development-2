package com.example.chatapp;

public class Message {
    private String id;
    private String senderId;
    private String text;

    public Message() {
    }

    public Message(String id, String senderId, String text) {
        this.id = id;
        this.senderId = senderId;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
