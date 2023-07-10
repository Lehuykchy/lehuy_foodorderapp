package com.example.foodorderapp.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.foodorderapp.model.CentreSP;
import com.example.foodorderapp.model.Messages;
import com.example.foodorderapp.repository.CentreSPRepo;
import com.example.foodorderapp.repository.FireBaseCentreSP;

public class CentreSPViewModel extends ViewModel {
    private CentreSPRepo centreSPRepo= new FireBaseCentreSP();
    public void addCentreSp(CentreSP centreSP){
        centreSPRepo.addCentreSp(centreSP);
    }


    public void updateCentre(String nameReceiver, String idReceiver, String id) {
        centreSPRepo.updateCentre(nameReceiver, idReceiver, id);
    }
    public void addMessage(Messages messages, String id) {
        centreSPRepo.addMessage(messages, id);
    }


}
