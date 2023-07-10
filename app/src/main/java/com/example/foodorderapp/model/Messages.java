package com.example.foodorderapp.model;

public class Messages {

    private String message;
    private String sendID;
    private String time;


    public Messages(String message, String sendID, String time) {
        this.message = message;
        this.sendID = sendID;
        this.time = time;
    }

    public Messages() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendID() {
        return sendID;
    }

    public void setSendID(String sendID) {
        this.sendID = sendID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
