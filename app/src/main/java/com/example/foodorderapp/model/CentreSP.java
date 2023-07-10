package com.example.foodorderapp.model;

import android.os.Build;

import java.time.LocalDateTime;

public class CentreSP {
    private String idSender;
    private String idReceiver;
    private String emailSender;
    private String emailReceiver;
    private String idPhotoSender;
    private String idPhotoReceiver;
    private boolean statusRequest;
    private String id;


    public CentreSP(String id, String idSender, String idReceiver, String emailSender, String emailReceiver
            , String idPhotoSender, String idPhotoReceiver, boolean statusRequest) {
        this.id = id;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
        this.emailSender = emailSender;
        this.emailReceiver = emailReceiver;
        this.idPhotoSender = idPhotoSender;
        this.idPhotoReceiver = idPhotoReceiver;
        this.statusRequest = statusRequest;

    }


    public CentreSP() {
    }

    public CentreSP(String idSender, String emailSender, boolean statusRequest) {
        this.idSender = idSender;
        this.emailSender = emailSender;
        this.statusRequest = statusRequest;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getEmailReceiver() {
        return emailReceiver;
    }

    public void setEmailReceiver(String emailReceiver) {
        this.emailReceiver = emailReceiver;
    }

    public String getIdPhotoSender() {
        return idPhotoSender;
    }

    public void setIdPhotoSender(String idPhotoSender) {
        this.idPhotoSender = idPhotoSender;
    }

    public String getIdPhotoReceiver() {
        return idPhotoReceiver;
    }

    public void setIdPhotoReceiver(String idPhotoReceiver) {
        this.idPhotoReceiver = idPhotoReceiver;
    }

    public boolean isStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(boolean statusRequest) {
        this.statusRequest = statusRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
