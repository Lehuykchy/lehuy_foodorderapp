package com.example.foodorderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.model.BillFood;
import com.example.foodorderapp.model.Messages;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MessagesChatAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<Messages> listMessages;

    private final int ITEM_SEND=1;
    private final int ITEM_RECIEVE=2;

    public MessagesChatAdapter(Context context, List<Messages> listMessages) {
        this.context = context;
        this.listMessages = listMessages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==ITEM_SEND)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false);
            return new SenderViewHolder(view);
        }
        else
        {
            View view= LayoutInflater.from(context).inflate(R.layout.item_chat_receiver,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages messages=listMessages.get(position);
        if(holder.getClass()==SenderViewHolder.class)
        {
            SenderViewHolder viewHolder=(SenderViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
        }
        else
        {
            RecieverViewHolder viewHolder=(RecieverViewHolder)holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
        }

    }

    @Override
    public int getItemViewType(int position) {
        Messages messages=listMessages.get(position);
        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSendID()))

        {
            return  ITEM_SEND;
        }
        else
        {
            return ITEM_RECIEVE;
        }
    }

    @Override
    public int getItemCount() {
        if(listMessages  != null){
            return listMessages.size();
        }
        return 0;
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textViewmessaage;


        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage = itemView.findViewById(R.id.chatsend);

        }
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textViewmessaage;



        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage = itemView.findViewById(R.id.chatreceiver);

        }
    }
    public void setDataList(List<Messages> newDataList) {
        this.listMessages = newDataList;
    }
}
