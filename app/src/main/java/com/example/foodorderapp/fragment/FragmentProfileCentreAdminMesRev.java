package com.example.foodorderapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.MessagesAdapter;
import com.example.foodorderapp.model.CentreSP;
import com.example.foodorderapp.viewmodel.CentreSPViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FragmentProfileCentreAdminMesRev extends Fragment {
    private View mView;
    private NavController navController;
    private MessagesAdapter messagesAdapter;
    private List<CentreSP> listCentreSP;
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_profile_centresupport_admin_mesrev, container, false);
        initUI();

        return mView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        view.findViewById(R.id.imgcentrespadminmesrev_back).setOnClickListener(v -> {
            navController.navigateUp();
        });

    }

    private void initUI() {
        recyclerView = mView.findViewById(R.id.rcv_centerspadminmesrev);

        listCentreSP = new ArrayList<>();
        getListCentreSP();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(listCentreSP, getActivity(), new MessagesAdapter.ICLickItemMesseageListener() {
            @Override
            public void onClickItemMesseage(int position) {
                CentreSP centreSP = messagesAdapter.GetCentreSP(position);
                Gson gson = new Gson();
                String json = gson.toJson(centreSP);
                Bundle bundle = new Bundle();
                bundle.putString("centreSP", json);
                navController.navigate(R.id.action_fragmentProfileCentreAdminMesRev_to_fragmentProfileCentreSPChat, bundle);
            }
        });
        recyclerView.setAdapter(messagesAdapter);

    }

    private void getListCentreSP() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String uid = currentUser.getUid();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference messeage = database.collection("messages");
        messeage.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CentreSP centreSP = document.toObject(CentreSP.class);
                                if(centreSP.getIdReceiver().equals(uid)){
                                    listCentreSP.add(centreSP);
                                }

                            }
                            messagesAdapter.notifyDataSetChanged();
                        }
                    }
                });

    }
}


