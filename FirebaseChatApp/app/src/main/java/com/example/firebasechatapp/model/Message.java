package com.example.firebasechatapp.model;

public class Message {
    private String id;

    private String userId;
    private String receiverId;
    private String userId_receiverId;
    private String text;
    private String receiverName;
    private String time;

    public Message() {

    }

    public Message(String id, String userId, String receiverId, String userId_receiverId, String text, String receiverName, String time) {
        this.id = id;
        this.userId = userId;
        this.receiverId = receiverId;
        this.text = text;
        this.userId_receiverId = userId_receiverId;
        this.receiverName = receiverName;
        this.time = time;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserId_receiverId() {
        return userId_receiverId;
    }

    public void setUserId_receiverId(String userId_receiverId) {
        this.userId_receiverId = userId_receiverId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
