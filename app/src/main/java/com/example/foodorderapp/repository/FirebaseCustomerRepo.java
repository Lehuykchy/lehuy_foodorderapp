package com.example.foodorderapp.repository;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.foodorderapp.model.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import org.checkerframework.checker.nullness.qual.NonNull;

public class FirebaseCustomerRepo implements CustomerRepo{
    private static final String CUSTOMER_COLLECTION = "customers";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void addCustomer(Customer customer) {
//        progressDialog.show();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String uid = currentUser.getUid();


        //
        db.collection(CUSTOMER_COLLECTION).document(uid).set(customer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirebaseUserRepository", "Error adding user", e);
                    }
                });
    }
}
