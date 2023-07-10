package com.example.foodorderapp.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class FragmentProfileCentreSPAdmin extends Fragment {
    private NavController navController;
    private RecyclerView recyclerView;
    private TextView tvMesReceived, tvMesHistory;
    private MessagesAdapter messagesAdapter;
    private List<CentreSP> listCentreSP;
    private CentreSPViewModel centreSPViewModel;
    private View mView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_centresupport_admin, container, false);
        initUI();
        tvMesReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickTvMesRev();
            }
        });
        return mView;
    }

    private void onCLickTvMesRev() {
        navController.navigate(R.id.action_fragmentProfileCentreSPAdmin_to_fragmentProfileCentreAdminMesRev);
    }

    private void initUI() {
        recyclerView = mView.findViewById(R.id.rcv_centerspadmin);
        tvMesReceived = mView.findViewById(R.id.tv_centrespadminmes);
        tvMesHistory= mView.findViewById(R.id.tv_centrespadminmeshistory);
        centreSPViewModel = new CentreSPViewModel();

        listCentreSP = new ArrayList<>();
        getListCentreSP();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(listCentreSP, getActivity(), new MessagesAdapter.ICLickItemMesseageListener() {
            @Override
            public void onClickItemMesseage(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bạn có trả lời tin nhắn này không??")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = auth.getCurrentUser();
                                CentreSP centreSP = messagesAdapter.GetCentreSP(position);
                                centreSPViewModel.updateCentre(currentUser.getEmail(), currentUser.getUid(), centreSP.getId());

                                centreSP.setIdReceiver(currentUser.getUid());
                                centreSP.setEmailReceiver(currentUser.getEmail());
                                centreSP.setStatusRequest(true);
                                Gson gson = new Gson();
                                String json = gson.toJson(centreSP);
                                Bundle bundle = new Bundle();
                                bundle.putString("centreSP", json);
                                navController.navigate(R.id.action_fragmentProfileCentreSPAdmin_to_fragmentProfileCentreSPChat, bundle);

                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();


            }
        });
        recyclerView.setAdapter(messagesAdapter);
    }

    private void getListCentreSP() {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        CollectionReference messeage = database.collection("messages");
        messeage.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CentreSP centreSP = document.toObject(CentreSP.class);
                                if(centreSP.isStatusRequest() == false){
                                    listCentreSP.add(centreSP);
                                }

                            }
                            messagesAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        view.findViewById(R.id.imgcentrespadmin_back).setOnClickListener(v -> {
            navController.navigateUp();
        });
    }
}
