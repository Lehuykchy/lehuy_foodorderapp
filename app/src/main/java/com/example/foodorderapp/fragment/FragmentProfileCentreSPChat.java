package com.example.foodorderapp.fragment;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.MessagesChatAdapter;
import com.example.foodorderapp.model.BillFood;
import com.example.foodorderapp.model.Cart;
import com.example.foodorderapp.model.CentreSP;
import com.example.foodorderapp.model.Messages;
import com.example.foodorderapp.viewmodel.CentreSPViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class FragmentProfileCentreSPChat extends Fragment {
    private NavController navController;
    private RecyclerView recyclerView;
    private TextView tvSend;
    private EditText edtChat;
    private CentreSPViewModel centreSPViewModel;
    private View mView;
    private boolean status = false;
    private MessagesChatAdapter messagesChatAdapter;
    private List<Messages> listMessages;
    private CentreSP centreSP;
    private  Bundle bundle;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_profile_centresupport_chat, container, false);
        bundle = getArguments();
        initUI();
        onClickTvSend();
        getListMes();
        onClickEdtChat();


//        getNewList();
        return mView;
    }
    private void getNewList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("messages").document(centreSP.getId()).collection("chat");
        col.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Xử lý lỗi nếu có
                    return;
                }
                if (value != null) {
                    int size = value.getDocuments().size();
                    if (size > 0) {
                        DocumentSnapshot lastDocument = value.getDocuments().get(size - 1);
                        Messages messages = lastDocument.toObject(Messages.class);
                        Messages messages1 = listMessages.get(listMessages.size() -1);
                        if(messages1.getTime().equals(messages.getTime())){
                            return;
                        }
                        listMessages.add(messages);
                        messagesChatAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(listMessages.size()-1);

                    }
                }

            }
        });
    }

    private void onClickEdtChat() {
        edtChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyBoard();
            }
        });
    }

    private void onClickTvSend() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String idSender = currentUser.getUid();


        if (bundle != null) {
            String json = bundle.getString("centreSP");

            Gson gson = new Gson();
            centreSP = gson.fromJson(json, CentreSP.class);
            Toast.makeText(getActivity(), centreSP.getEmailSender(), Toast.LENGTH_SHORT).show();

            tvSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtChat.getText().toString() != "" || edtChat.getText().toString() != null) {
                        if(getTime() == ""){
                            Toast.makeText(getActivity(), "maycu", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Messages messages = new Messages(edtChat.getText().toString(), idSender, getTime());
                        centreSPViewModel.addMessage(messages, centreSP.getId());
                        edtChat.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Nhập tin nhắn !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {

            String email = currentUser.getEmail();
            centreSP = new CentreSP(idSender, email, false);
            centreSP.setId(idSender + getTime());


            tvSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtChat.getText().toString() != "" || edtChat.getText().toString() != null) {

                        Messages messages = new Messages(edtChat.getText().toString(), idSender, getTime());

                        if (status == false) {
                            centreSPViewModel.addCentreSp(centreSP);
                            centreSPViewModel.addMessage(messages, centreSP.getId());
                            edtChat.setText("");
                            status = true;

                        } else {
                            centreSPViewModel.addMessage(messages, centreSP.getId());
                            edtChat.setText("");
                        }

                    } else {
                        Toast.makeText(getActivity(), "Nhập tin nhắn !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

    private String getTime() {
        String time = "";
        LocalDateTime current = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            current = LocalDateTime.now();
        }
        //sử dụng class DateTimeFormatter để định dạng ngày giờ theo kiểu pattern
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        }
        //sử dụng phương thức format() để định dạng ngày giờ hiện tại rồi gán cho chuỗi formatted
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String formatted = current.format(formatter);
            time = formatted;
        }
        return time;
    }

    private void initUI() {
        edtChat = mView.findViewById(R.id.edtcentrespchat);
        tvSend = mView.findViewById(R.id.tvcentrespsend);
        centreSPViewModel = new CentreSPViewModel();
        recyclerView = mView.findViewById(R.id.rcvcentrespchat);

        listMessages = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        messagesChatAdapter = new MessagesChatAdapter(getActivity(), listMessages);
        recyclerView.setAdapter(messagesChatAdapter);

    }

    private void getListMes() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference col = db.collection("messages").document(centreSP.getId()).collection("chat");
        col.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            Messages messages = document.toObject(Messages.class);
                            listMessages.add(messages);
                        }

                    }
                    messagesChatAdapter.notifyDataSetChanged();
                    if(listMessages != null){
                        recyclerView.scrollToPosition(listMessages.size()-1);
                    }
                    Log.d("checkdb", "getNewList: " + String.valueOf(listMessages.size()));
                    getNewList();

                }
            }
        });




    }


    private  void checkKeyBoard(){
        final View avtivityView = mView.findViewById(R.id.activityview);
        avtivityView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                avtivityView.getWindowVisibleDisplayFrame(r);
                int height = avtivityView.getRootView().getHeight() - r .height();
                if(height > 0.25*avtivityView.getRootView().getHeight()){
                    if(listMessages.size()>0){
                        recyclerView.scrollToPosition(listMessages.size()-1);
                        avtivityView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }

            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        view.findViewById(R.id.imgcentrespchat_back).setOnClickListener(v -> {
            navController.navigateUp();
        });
    }
}
