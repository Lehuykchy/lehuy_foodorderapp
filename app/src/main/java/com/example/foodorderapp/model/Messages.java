package com.example.foodorderapp.model;

public class Messages {

    private String massage;
    private String id;

    public Messages(String massage, String id) {
        this.massage = massage;
        this.id = id;
    }

    public Messages() {
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
