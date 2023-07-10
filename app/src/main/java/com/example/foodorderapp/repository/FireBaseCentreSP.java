package com.example.foodorderapp.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodorderapp.model.CentreSP;
import com.example.foodorderapp.model.Messages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FireBaseCentreSP implements CentreSPRepo{
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    @Override
    public void addCentreSp(CentreSP centreSP) {
        DocumentReference messeage = database.collection("messages").document(centreSP.getId());

        messeage.set(centreSP).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

    }

    @Override
    public void updateCentre(String nameReceiver, String idReceiver, String id) {
        DocumentReference mes = database.collection("messages").document(id);
        mes.update("emailReceiver", nameReceiver)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                    }
                });

        mes.update("idReceiver", idReceiver)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                    }
                });
        mes.update("statusRequest", true)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@org.checkerframework.checker.nullness.qual.NonNull Exception e) {
                    }
                });
    }

    @Override
    public void addMessage(Messages messages, String id) {
        DocumentReference mes = database.collection("messages").document(id);
        DocumentReference collectionReference = mes.collection("chat").document("chat||"+messages.getTime());
        collectionReference.set(messages).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
    }
}
