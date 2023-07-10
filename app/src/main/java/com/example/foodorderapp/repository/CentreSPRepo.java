package com.example.foodorderapp.repository;

import com.example.foodorderapp.model.CentreSP;
import com.example.foodorderapp.model.Messages;

public interface CentreSPRepo {
    void addCentreSp(CentreSP centreSP);
    void updateCentre(String nameReceiver, String idReceiver, String id);
    void addMessage(Messages messages, String id);
}
